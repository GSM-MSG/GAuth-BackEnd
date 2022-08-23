package com.msg.gauth.domain.user.services;

import javax.mail.Message.RecipientType;
import com.msg.gauth.domain.user.EmailAuthEntity;
import com.msg.gauth.domain.user.presentation.dto.request.EmailSendDto;
import com.msg.gauth.domain.user.repository.EmailAuthRepository;
import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.exceptions.MessageSendFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
@Slf4j
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
                .authentication(false)
                .randomValue(value)
                .email(email)
                .build();
        emailAuthRepository.save(authEntity);
        try{
            MimeMessage message = mailSender.createMimeMessage();
            message.addRecipients(RecipientType.TO,emailSendDto.getEmail());
            message.setSubject("[Gauth] 이메일 인즘");
            message.setText("<form action=\"http://localhost:8080/v1/email/authentication?email="+emailSendDto.getEmail()+"&uuid="+value+"\"><input type=\"submit\"></input></form>", "utf-8", "html");
            mailSender.send(message);
        }catch (MessagingException ex){

        }
    }
}
