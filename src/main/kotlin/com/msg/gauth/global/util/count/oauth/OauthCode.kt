package com.msg.gauth.global.util.count.oauth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "oauthCode", timeToLive = 60 * 5)
class OauthCode(
    @Id
    val code: String,
    val email: String,
)