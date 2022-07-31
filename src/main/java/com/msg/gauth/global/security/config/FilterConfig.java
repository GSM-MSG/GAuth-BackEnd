package com.msg.gauth.global.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msg.gauth.global.security.filter.ExceptionFilter;
import com.msg.gauth.global.security.filter.JwtTokenFilter;
import com.msg.gauth.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void configure(HttpSecurity builder) {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(jwtTokenProvider);
        ExceptionFilter exceptionFilter = new ExceptionFilter(objectMapper);

        builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        builder.addFilterBefore(exceptionFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
