package com.msg.gauth.domain.email

import org.hibernate.annotations.ColumnDefault
import org.hibernate.validator.constraints.Length
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash

@RedisHash(value = "emailAuth", timeToLive = 60 * 15)
class EmailAuthEntity(
    @Id
    val email: String,

    val randomValue: @Length(max = 36) String,

    val authentication: Boolean,

    val attemptCount: Int = 1,
) {

    fun updateAuthentication(authentication: Boolean): EmailAuthEntity {
        return EmailAuthEntity(
            email = this.email,
            attemptCount = this.attemptCount,
            randomValue = this.randomValue,
            authentication = authentication,
        )
    }

    fun resendEmailAuth(uuid: String): EmailAuthEntity {
        return EmailAuthEntity(
            email = this.email,
            authentication = this.authentication,
            attemptCount = this.attemptCount+1,
            randomValue = uuid,
        )
    }
}