package com.msg.gauth.domain.auth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash
class RefreshToken(
    @Id
    val userId: Long,

    @Indexed
    var token: String,

    @TimeToLive
    var timeToLive: Long
) {
    fun updateToken(token: String, timeToLive: Long) {
        this.token = token
        this.timeToLive = timeToLive
    }
}