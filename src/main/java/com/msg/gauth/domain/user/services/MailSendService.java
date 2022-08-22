package com.msg.gauth.domain.user.services;

import com.msg.gauth.domain.user.dto.EmailSendDto;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
@RequiredArgsConstructor
public class MailSendService {
    private final JavaMailSender mailSender;

    @Async
    public void execute(EmailSendDto emailSendDto){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailSendDto.getEmail());
        message.setSubject("[Gauth] 이메일 인즘");
        message.setText("<form action=\"http://localhost:8080/v1/email/authentication?email="+emailSendDto.getEmail()+"\"><input type=\"submit\"></input></form>");
        mailSender.send(message);
    }
}
