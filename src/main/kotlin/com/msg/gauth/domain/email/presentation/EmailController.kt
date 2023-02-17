package com.msg.gauth.domain.email.presentation

import com.msg.gauth.domain.email.presentation.dto.EmailSendDto
import com.msg.gauth.domain.email.services.MailSendService
import com.msg.gauth.domain.email.services.MailVerificationCheckService
import com.msg.gauth.domain.email.services.MailVerificationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/email")
class EmailController(
    private val mailSendService: MailSendService,
    private val mailVerificationService: MailVerificationService,
    private val mailVerificationCheckService: MailVerificationCheckService,
) {
    @PostMapping
    fun emailSend(@RequestBody emailSendDto: EmailSendDto): ResponseEntity<Void> {
        mailSendService.execute(emailSendDto)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/authentication")
    fun emailVerification(@RequestParam email: String, @RequestParam uuid: String): ResponseEntity<String> {
        mailVerificationService.execute(email, uuid)
        return ResponseEntity.ok("완료되었습니다!<br> 회원가입을 진행해주세요.")
    }

    @GetMapping
    fun checkEmailVerification(@RequestParam email: String): ResponseEntity<Void>{
        mailVerificationCheckService.execute(email)
        return ResponseEntity.ok().build()
    }

}