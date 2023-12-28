package com.msg.gauth.domain.auth.handler

import com.msg.gauth.domain.auth.event.SignupLoggingEvent
import com.msg.gauth.global.thirdparty.discord.properties.DiscordProperties
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.springframework.context.event.EventListener
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import org.springframework.web.client.RestTemplate

@Component
class AuthEventHandler(
    private val discordProperties: DiscordProperties
) {
    @EventListener
    fun signupLoggingHandler(signupLoggingEvent: SignupLoggingEvent) {
        val fields = listOf(
            JsonObject(
                mapOf(
                    "name" to JsonPrimitive("이메일"),
                    "value" to JsonPrimitive(signupLoggingEvent.email)
                )
            )
        )
        val embed = JsonObject(
            mapOf(
                "title" to JsonPrimitive("회원가입 알림 확인하기"),
                "url" to JsonPrimitive("https://gauth.co.kr/stulist?type=applicant"),
                "description" to JsonPrimitive("새로운 회원가입 요청이 왔어요!"),
                "color" to JsonPrimitive(48127),
                "fields" to JsonArray(fields)
            )
        )
        val jsonObject = JsonObject(
            mapOf(
                "content" to JsonPrimitive("## [알림] 새로운 회원가입 요청!"),
                "embeds" to JsonArray(listOf(embed))
            )
        )

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val restTemplate = RestTemplate()
        val entity = HttpEntity(jsonObject.toString(), headers)
        restTemplate.postForObject(discordProperties.url, entity, String::class.java)
    }
}
