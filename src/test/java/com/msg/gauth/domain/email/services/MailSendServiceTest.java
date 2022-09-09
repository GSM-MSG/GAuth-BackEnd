package com.msg.gauth.domain.email.services;

import com.msg.gauth.domain.email.repository.EmailAuthRepository;
import com.msg.gauth.domain.email.presentation.dto.request.EmailSendDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class MailSendServiceTest {
    @Autowired
    MailSendService mailSendService;
    @Autowired
    EmailAuthRepository emailAuthRepository;

    @Test
    public void test() throws MessagingException {
        EmailSendDto emailSendDto = new EmailSendDto("baegteun@gmail.com");
        mailSendService.execute(emailSendDto);
        emailAuthRepository.deleteById(emailSendDto.getEmail());
    }
}