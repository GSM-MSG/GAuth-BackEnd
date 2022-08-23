package com.msg.gauth.domain.email.services;

import com.msg.gauth.domain.email.EmailAuthEntity;
import com.msg.gauth.domain.email.exception.AuthCodeExpiredException;
import com.msg.gauth.domain.email.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailVerificationService {
    private final EmailAuthRepository emailAuthRepository;

    @Transactional
    public void execute(String email, String uuid) {
        EmailAuthEntity emailAuth = emailAuthRepository.findById(email)
                .orElseThrow(AuthCodeExpiredException::new);
        if (!Objects.equals(emailAuth.getRandomValue(), uuid))
            throw new AuthCodeExpiredException();
        emailAuth.updateAuthentication(true);
    }
}
