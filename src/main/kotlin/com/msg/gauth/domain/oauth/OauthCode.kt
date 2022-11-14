package com.msg.gauth.domain.oauth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "OauthRefreshToken", timeToLive = 1000L * 60 * 5)
class OauthCode(
    @Id
    val code: String,
    val email: String,
)