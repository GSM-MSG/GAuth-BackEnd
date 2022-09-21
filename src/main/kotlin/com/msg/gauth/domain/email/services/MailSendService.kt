package com.msg.gauth.domain.email.services

import com.msg.gauth.domain.email.EmailAuthEntity
import com.msg.gauth.domain.email.exception.ManyRequestEmailAuthException
import com.msg.gauth.domain.email.presentation.dto.EmailSendDto
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.global.exception.ErrorCode
import com.msg.gauth.global.exception.exceptions.MessageSendFailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException

@Service
@EnableAsync
class MailSendService(
    private val mailSender: JavaMailSender,
    private val emailAuthRepository: EmailAuthRepository
) {

    @Async
    @Transactional
    fun execute(emailSendDto: EmailSendDto) {
        val email: String = emailSendDto.email
        val value = UUID.randomUUID().toString()
        val authEntity = emailAuthRepository.findById(email)
            .orElse(
                EmailAuthEntity(
                    authentication = false,
                    randomValue = value,
                    email = email,
                    attemptCount = 0
                )
            )
        if (authEntity.attemptCount >= 3) throw ManyRequestEmailAuthException()
        authEntity.updateRandomValue(value)
        authEntity.increaseAttemptCount()
        emailAuthRepository.save(authEntity)
        try {
            val message = mailSender.createMimeMessage()
            val msg =
                "<a href=\"http://localhost:8080/email/authentication?email=$email&uuid=$value\" style=\"padding: 10px; border: none; color: white; background-color: skyblue; border-radius: 8px; align-self: center; text-align: center;\">인증하기</a>"
            message.addRecipients(Message.RecipientType.TO, emailSendDto.email)
            message.subject = "[Gauth] 이메일 인증"
            message.setText(msg, "utf-8", "html")
            mailSender.send(message)
        } catch (ex: MessagingException) {
            throw MessageSendFailException()
        }
    }
}