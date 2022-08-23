package com.msg.gauth.domain.email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "emailAuth", timeToLive = 60 * 15)
public class EmailAuthEntity {
    @Id
    private String email;

    @Length(max = 36)
    private String randomValue;
    private Boolean authentication;

    @ColumnDefault("1")
    private Integer attemptCount;

    public void updateAuthentication(Boolean authentication) {
        this.authentication = authentication;
    }
    public void increaseAttemptCount() {
        this.attemptCount += 1;
    }
}
