package com.msg.gauth.domain.user.services;

import com.msg.gauth.domain.user.EmailAuthEntity;
import com.msg.gauth.domain.user.dto.EmailSendDto;
import com.msg.gauth.domain.user.repository.EmailAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@EnableAsync
@RequiredArgsConstructor
public class MailSendService {
    private final JavaMailSender mailSender;
    private final EmailAuthRepository emailAuthRepository;

    @Async
    public void execute(EmailSendDto emailSendDto){
        String email = emailSendDto.getEmail();
        String value = UUID.randomUUID().toString();
        EmailAuthEntity authEntity = EmailAuthEntity.builder()
                .Authentication(false)
                .randomValue(value)
                .email(email)
                .build();
        emailAuthRepository.save(authEntity);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailSendDto.getEmail());
        message.setSubject("[Gauth] 이메일 인즘");
        message.setText("<form action=\"http://localhost:8080/v1/email/authentication?email="+emailSendDto.getEmail()+"&uuid="+value+"\"><input type=\"submit\"></input></form>");
        mailSender.send(message);
    }
}
