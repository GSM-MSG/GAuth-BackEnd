package com.msg.gauth.global.util.count.auth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(value = "minuteSignInCount", timeToLive = 60)
class MinuteSignInCount(
    @Id
    @Indexed
    val email: String,
) {
    var count: Int = 0
        private set
    fun addCount() =
        count++
}