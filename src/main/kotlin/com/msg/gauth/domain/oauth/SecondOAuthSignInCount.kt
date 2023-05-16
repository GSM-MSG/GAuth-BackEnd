package com.msg.gauth.domain.oauth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "secondOAuthSignInCount", timeToLive = 5)
class SecondOAuthSignInCount(
    @Id
    @Indexed
    val email: String,
) {
    var count: Int = 0
        private set
    fun addCount() =
        count++
}