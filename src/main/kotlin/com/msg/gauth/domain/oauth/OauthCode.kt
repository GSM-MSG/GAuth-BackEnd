package com.msg.gauth.domain.oauth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "oauthCode", timeToLive = 60 * 5)
class OauthCode(
    @Id
    val code: String,
    val email: String,
)