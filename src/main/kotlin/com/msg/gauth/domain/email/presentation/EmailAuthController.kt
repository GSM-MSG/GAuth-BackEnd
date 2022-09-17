package com.msg.gauth.domain.email.presentation

import com.msg.gauth.domain.email.presentation.dto.EmailSendDto
import com.msg.gauth.domain.email.services.MailSendService
import com.msg.gauth.domain.email.services.MailVerificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/email")
class EmailAuthController(
    private val mailSendService: MailSendService,
    private val mailVerificationService: MailVerificationService
) {
    @PostMapping
    fun emailSend(@RequestBody emailSendDto: EmailSendDto): ResponseEntity<Void> {
        mailSendService.execute(emailSendDto)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/authentication")
    fun emailVerification(@RequestParam email: String, @RequestParam uuid: String): ResponseEntity<Void> {
        mailVerificationService.execute(email, uuid)
        return ResponseEntity.noContent().build()
    }
}