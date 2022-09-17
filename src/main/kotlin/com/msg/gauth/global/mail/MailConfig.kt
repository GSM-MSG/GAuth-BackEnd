package com.msg.gauth.global.mail

import com.msg.gauth.global.mail.properties.MailProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig(
    private val mailProperties: MailProperties
) {
    @Bean
    fun getJavaMailSender(): JavaMailSender {
        var sender = JavaMailSenderImpl()
        sender.host = mailProperties.host
        sender.port = mailProperties.port
        sender.username = mailProperties.username
        sender.password = mailProperties.password

        sender.javaMailProperties["mail.smtp.auth"] = true
        sender.javaMailProperties["mail.smtp.connectiontimeout"] = 5000
        sender.javaMailProperties["mail.smtp.timeout"] = 5000
        sender.javaMailProperties["mail.smtp.writetimeout"] = 5000
        sender.javaMailProperties["mail.transport.protocol"] = "smtp"
        sender.javaMailProperties["mail.smtp.starttls.enable"] = true
        sender.javaMailProperties["mail.smtp.starttls.required"] = true

        return sender
    }
}