package com.msg.gauth.domain.email.services

import com.msg.gauth.domain.email.EmailAuthEntity
import com.msg.gauth.domain.email.exception.AlreadyAuthenticatedEmailException
import com.msg.gauth.domain.email.exception.ManyRequestEmailAuthException
import com.msg.gauth.domain.email.presentation.dto.EmailSendDto
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.exception.exceptions.MessageSendFailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.util.*
import javax.mail.Message
import javax.mail.MessagingException

@Service
class MailSendService(
    private val mailSender: JavaMailSender,
    private val emailAuthRepository: EmailAuthRepository,
    private val templateEngine: SpringTemplateEngine
) {

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
        if(authEntity.authentication)
            throw AlreadyAuthenticatedEmailException()
        if (authEntity.attemptCount >= 5) throw ManyRequestEmailAuthException()
        val updateEmailAuth = authEntity.resendEmailAuth(value)
        emailAuthRepository.save(updateEmailAuth)
        try {
            val message = mailSender.createMimeMessage()
            val mailTemplate = createMailTemplate(email, value)
            message.addRecipients(Message.RecipientType.TO, emailSendDto.email)
            message.subject = "[GAuth] 이메일 인증"
            message.setText(mailTemplate, "utf-8", "html")
            mailSender.send(message)
        } catch (ex: MessagingException) {
            throw MessageSendFailException()
        }
    }

    private fun createMailTemplate(email: String, code: String): String {
        val context = Context()
        context.setVariables(mapOf(
            "email" to email,
            "code" to code
        ))
        return templateEngine.process("mail", context)
    }
}