package com.msg.gauth.domain.email.service

import com.msg.gauth.domain.email.EmailAuthEntity
import com.msg.gauth.domain.email.exception.AlreadyAuthenticatedEmailException
import com.msg.gauth.domain.email.exception.ManyRequestEmailAuthException
import com.msg.gauth.domain.email.presentation.dto.EmailSendDto
import com.msg.gauth.domain.email.repository.EmailAuthRepository
import com.msg.gauth.global.exception.exceptions.MessageSendFailException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine
import java.util.*
import javax.mail.MessagingException

@Service
class SendMailService(
    private val mailSender: JavaMailSender,
    private val emailAuthRepository: EmailAuthRepository,
    private val templateEngine: SpringTemplateEngine
) {

    fun execute(emailSendDto: EmailSendDto) {
        val email: String = emailSendDto.email

        val value = UUID.randomUUID().toString()

        val authEntity = emailAuthRepository.findByIdOrNull(email)
            ?: EmailAuthEntity(
                authentication = false,
                randomValue = value,
                email = email,
                attemptCount = 0
            )

        if(authEntity.authentication)
            throw AlreadyAuthenticatedEmailException()

        if (authEntity.attemptCount >= 5)
            throw ManyRequestEmailAuthException()

        val updateEmailAuth = authEntity.resendEmailAuth(value)

        emailAuthRepository.save(updateEmailAuth)

        try {
            val message = mailSender.createMimeMessage()

            val helper = MimeMessageHelper(message, "UTF-8")

            val mailTemplate = createMailTemplate(email, value)

            helper.setSubject("[GAuth] 이메일 인증")
            helper.setTo(emailSendDto.email)
            helper.setText(mailTemplate, true)

            mailSender.send(message)
        } catch (ex: MessagingException) {
            throw MessageSendFailException()
        }
    }

    private fun createMailTemplate(email: String, code: String): String {
        val context = Context()

        val url = "https://port-0-gauth-backend-85phb42bluutn9a7.sel5.cloudtype.app/email/authentication?email=${email}&uuid=${code}"

        context.setVariables(
            mapOf(
                "url" to url
            )
        )
        return templateEngine.process("mail-template", context)
    }
}