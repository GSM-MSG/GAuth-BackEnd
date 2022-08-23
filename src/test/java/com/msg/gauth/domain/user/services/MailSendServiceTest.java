package com.msg.gauth.domain.user.services;

import com.msg.gauth.domain.email.services.MailSendService;
import com.msg.gauth.domain.user.presentation.dto.request.EmailSendDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
class MailSendServiceTest {
    @Autowired
    MailSendService mailSendService;
    @Test
    public void test() throws MessagingException {
        EmailSendDto emailSendDto = new EmailSendDto("k01066624566@gmail.com");
        mailSendService.execute(emailSendDto);
    }
}