package com.msg.gauth.domain.auth

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive
import org.springframework.data.redis.core.index.Indexed

@RedisHash
class RefreshToken(
    @Id
    val userId: Long,
    token: String,
    timeToLive: Long,
) {
    @Indexed
    var token: String = token
    private set

    @TimeToLive
    var timeToLive: Long = timeToLive
    private set
    fun updateToken(token: String, timeToLive: Long) {
        this.token = token
        this.timeToLive = timeToLive
    }
}