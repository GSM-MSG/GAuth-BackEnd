package com.msg.gauth.domain.auth

import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "refreshToken", timeToLive = JwtTokenProvider.REFRESH_EXP)
class RefreshToken(
    @Id
    @Indexed
    val userId: Long,
    @Indexed
    val token: String,
)