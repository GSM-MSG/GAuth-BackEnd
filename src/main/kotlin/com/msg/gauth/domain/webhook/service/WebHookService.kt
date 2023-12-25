package com.msg.gauth.domain.webhook.service

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate

class WebHookService {

    @Value("\${discord.webhookURL}")
    private lateinit var url: String

    fun callEvent(email: String) {
        val contentMessage = "[알림] 회원가입을 시도하였습니다. Email: $email"
        val data = JsonObject(mapOf("content" to JsonPrimitive(contentMessage)))
        send(data)
    }

    private fun send(jsonData: JsonObject) {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val restTemplate = RestTemplate()
        val entity = HttpEntity(jsonData.toString(), headers)
        restTemplate.postForObject(url, entity, String::class.java)
    }
}
