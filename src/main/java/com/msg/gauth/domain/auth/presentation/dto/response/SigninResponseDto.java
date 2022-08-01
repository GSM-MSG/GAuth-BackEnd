package com.msg.gauth.domain.auth.presentation.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
public class SigninResponseDto {
    String accessToken;
    String refreshToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    ZonedDateTime expiresAt;
}
