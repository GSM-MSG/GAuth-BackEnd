package com.msg.gauth.domain.auth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "tempSignInBan", timeToLive = 60)
class TempSignInBan (
    @Id
    @Indexed
    val email: String
)