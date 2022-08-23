package com.msg.gauth.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "emailAuth", timeToLive = 60 * 15)
public class EmailAuthEntity {
    @Id
    private String email;
    private String randomValue;
    private Boolean authentication;
}
