package com.msg.gauth.domain.auth;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@RedisHash
public class RefreshToken {
    @Id
    Long userId;

    @Indexed
    String token;

    @TimeToLive
    Long timeToLive;

    public void updateToken(String token, Long timeToLive) {
        this.token = token;
        this.timeToLive = timeToLive;
    }
}
