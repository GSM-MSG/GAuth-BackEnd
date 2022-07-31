package com.msg.gauth.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.msg.gauth.global.exception.ErrorCode;
import com.msg.gauth.global.exception.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper;

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        String responseString = objectMapper.writeValueAsString(new ErrorResponse(errorCode));

        response.setStatus(errorCode.getCode());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(responseString);
    }
}
