package com.msg.gauth.domain.auth.services;

import com.msg.gauth.domain.email.EmailAuthEntity;
import com.msg.gauth.domain.email.repository.EmailAuthRepository;
import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.exception.EmailNotVerifiedException;
import com.msg.gauth.domain.user.presentation.dto.request.SignUpDto;
import com.msg.gauth.domain.user.repository.UserRepository;
import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailAuthRepository emailAuthRepository;

    @Transactional
    public Long execute(SignUpDto signUpDto){
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
        String password = signUpDto.getPassword();
        User user = signUpDto.toEntity(passwordEncoder.encode(password));
        EmailAuthEntity emailAuth = emailAuthRepository.findById(signUpDto.getEmail())
                .orElseThrow(EmailNotVerifiedException::new);
        if (!emailAuth.getAuthentication())
            throw new EmailNotVerifiedException();
        return userRepository.save(user).getId();
    }
}
