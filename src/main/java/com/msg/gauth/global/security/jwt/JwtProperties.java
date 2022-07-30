package com.msg.gauth.global.security.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@ConfigurationProperties(prefix = "jwt")
@RequiredArgsConstructor
public class JwtProperties {
    private final String accessSecret;
    private final String refreshSecret;
}
