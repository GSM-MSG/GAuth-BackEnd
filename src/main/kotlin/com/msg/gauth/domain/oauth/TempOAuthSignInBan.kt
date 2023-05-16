package com.msg.gauth.domain.oauth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "tempOAuthSignInBan", timeToLive = 60)
class TempOAuthSignInBan (
    @Id
    @Indexed
    val email: String
)