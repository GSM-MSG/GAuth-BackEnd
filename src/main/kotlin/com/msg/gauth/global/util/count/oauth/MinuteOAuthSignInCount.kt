package com.msg.gauth.global.util.count.oauth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "minuteOAuthSignInCount", timeToLive = 60)
class MinuteOAuthSignInCount(
    @Id
    @Indexed
    val email: String,
) {
    var count: Int = 0
        private set
    fun addCount() =
        count++
}