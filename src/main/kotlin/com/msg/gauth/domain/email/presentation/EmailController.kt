package com.msg.gauth.domain.email.presentation

import com.msg.gauth.domain.email.presentation.dto.EmailSendDto
import com.msg.gauth.domain.email.service.SendMailService
import com.msg.gauth.domain.email.service.CheckMailVerificationService
import com.msg.gauth.domain.email.service.VerifyMailService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/email")
class EmailController(
    private val sendMailService: SendMailService,
    private val verifyMailService: VerifyMailService,
    private val checkMailVerificationService: CheckMailVerificationService,
) {
    @PostMapping
    fun emailSend(@RequestBody emailSendDto: EmailSendDto): ResponseEntity<Void> {
        sendMailService.execute(emailSendDto)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/authentication")
    fun emailVerification(@RequestParam email: String, @RequestParam uuid: String): ResponseEntity<String> {
        verifyMailService.execute(email, uuid)
        return ResponseEntity.ok("완료되었습니다!<br> 다음 단계를 진행해주세요.")
    }

    @GetMapping
    fun checkEmailVerification(@RequestParam email: String): ResponseEntity<Void>{
        checkMailVerificationService.execute(email)
        return ResponseEntity.ok().build()
    }

}