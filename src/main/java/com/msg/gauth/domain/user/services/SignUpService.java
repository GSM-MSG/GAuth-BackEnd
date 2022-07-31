package com.msg.gauth.domain.user.services;

import com.msg.gauth.domain.user.User;
import com.msg.gauth.domain.user.enums.UserRole;
import com.msg.gauth.domain.user.presentation.dto.request.SignUpDto;
import com.msg.gauth.domain.user.repository.UserRepository;
import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.BasicException;
import com.msg.gauth.global.exception.exceptions.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long execute(SignUpDto signUpDto){
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            throw new DuplicateEmailException(ErrorCode.DUPLICATE_EMAIL);
        }
        String password = signUpDto.getPassword();
        User user = signUpDto.toEntity(passwordEncoder.encode(password));
        return userRepository.save(user).getId();
    }
}
