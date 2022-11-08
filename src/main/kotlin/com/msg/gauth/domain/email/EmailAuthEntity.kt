package com.msg.gauth.domain.email

import org.hibernate.annotations.ColumnDefault
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "emailAuth", timeToLive = 60 * 15)
class EmailAuthEntity(
    @Id
    val email: String,

    randomValue: String,

    authentication: Boolean,

    attemptCount: Int
) {

    var randomValue: @Length(max = 36) String = randomValue
    private set
    var authentication: Boolean = authentication
    private set
    @ColumnDefault("1")
    var attemptCount: Int = attemptCount
    private set

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