package com.msg.gauth.domain.email

import org.hibernate.annotations.ColumnDefault
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "emailAuth", timeToLive = 60 * 15)
class EmailAuthEntity(
    @Id
    val email: String,

    var randomValue: @Length(max = 36) String,


    var authentication: Boolean,

    @ColumnDefault("1")
    var attemptCount: Int
) {
    fun updateAuthentication(authentication: Boolean) {
        this.authentication = authentication
    }

    fun updateRandomValue(uuid: String) {
        randomValue = uuid
    }

    fun increaseAttemptCount() {
        attemptCount += 1
    }
}