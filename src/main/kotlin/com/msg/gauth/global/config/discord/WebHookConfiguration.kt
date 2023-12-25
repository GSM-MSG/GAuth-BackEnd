package com.msg.gauth.global.config.discord

import com.msg.gauth.domain.webhook.service.WebHookService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class WebHookConfiguration {

    @Bean
    fun webHookService(): WebHookService {
        return WebHookService()
    }

}