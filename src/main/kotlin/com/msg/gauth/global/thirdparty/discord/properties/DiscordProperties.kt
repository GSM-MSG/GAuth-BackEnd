package com.msg.gauth.global.thirdparty.discord.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "discord.webhook")
class DiscordProperties(
    val url: String
)