package com.msg.gauth.global.thirdparty.mail.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.mail")
data class MailProperties(
    val host: String,
    val port: Int,
    val username: String,
    val password: String
)