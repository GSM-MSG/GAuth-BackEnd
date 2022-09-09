package com.msg.gauth.domain.email.presentation;

import com.msg.gauth.domain.email.presentation.dto.request.EmailSendDto;
import com.msg.gauth.domain.email.services.MailSendService;
import com.msg.gauth.domain.email.services.MailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailAuthController {
    private final MailSendService mailSendService;
    private final MailVerificationService mailVerificationService;

    @PostMapping
    public ResponseEntity<Void> emailSend(@RequestBody EmailSendDto emailSendDto){
        mailSendService.execute(emailSendDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/authentication")
    public ResponseEntity<Void> emailVerification(@RequestParam String email, @RequestParam String uuid){
        mailVerificationService.execute(email, uuid);
        return ResponseEntity.noContent().build();
    }

}
