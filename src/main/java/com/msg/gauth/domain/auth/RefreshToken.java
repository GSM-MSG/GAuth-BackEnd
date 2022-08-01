package com.msg.gauth.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;


@RedisHash
@AllArgsConstructor
@Getter
public class RefreshToken {
    @Id
    private Long userId;

    @Indexed
    private String token;

    @TimeToLive
    private Long timeToLive;

    public void updateToken(String token, Long timeToLive) {
        this.token = token;
        this.timeToLive = timeToLive;
    }
}
