package com.msg.gauth.global.thymeleaf

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.nio.charset.StandardCharsets

@Configuration
class ThymeleafTemplateConfig {
    @Bean
    fun springTemplateEngine(): SpringTemplateEngine {
        val springTemplateEngine = SpringTemplateEngine()
        springTemplateEngine.addTemplateResolver(emailTemplateResolver())
        return springTemplateEngine
    }

    fun emailTemplateResolver(): ClassLoaderTemplateResolver {
        val emailTemplateResolver = ClassLoaderTemplateResolver()
        emailTemplateResolver.prefix = "/templates/"
        emailTemplateResolver.suffix = ".html"
        emailTemplateResolver.templateMode = TemplateMode.HTML
        emailTemplateResolver.characterEncoding = StandardCharsets.UTF_8.name()
        emailTemplateResolver.isCacheable = false
        return emailTemplateResolver
    }
}