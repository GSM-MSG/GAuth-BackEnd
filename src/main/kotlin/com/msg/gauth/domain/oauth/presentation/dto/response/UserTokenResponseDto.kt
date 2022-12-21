package com.msg.gauth.domain.oauth.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime

data class UserTokenResponseDto(
    val accessToken: String,
    val refreshToken: String
)