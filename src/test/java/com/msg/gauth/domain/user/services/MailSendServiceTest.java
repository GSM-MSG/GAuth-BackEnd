package com.msg.gauth.domain.user.services;

import com.msg.gauth.domain.user.dto.EmailSendDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailSendServiceTest {
    @Autowired MailSendService mailSendService;
    @Test
    public void test(){
        EmailSendDto emailSendDto = new EmailSendDto("k01066624566@gmail.com");
        mailSendService.execute(emailSendDto);
    }
}