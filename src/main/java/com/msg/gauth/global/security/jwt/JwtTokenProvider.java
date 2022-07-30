package com.msg.gauth.global.security.jwt;

import com.msg.gauth.global.security.auth.AuthDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final AuthDetailsService authDetailsService;

    static final String ACCESS_TYPE = "access";
    static final String REFRESH_TYPE = "refresh";
    static final Long ACCESS_EXP = 60L * 15; // 15 min
    static final Long REFRESH_EXP = 60L * 60 * 24 * 7; // 1 weeks


}
