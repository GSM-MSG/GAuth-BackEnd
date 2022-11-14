package com.msg.gauth.domain.oauth

import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "OauthRefreshToken", timeToLive = JwtTokenProvider.REFRESH_EXP)
class OauthRefreshToken(
    @Id
    @Indexed
    val userId: Long,
    @Indexed
    val token: String,
)