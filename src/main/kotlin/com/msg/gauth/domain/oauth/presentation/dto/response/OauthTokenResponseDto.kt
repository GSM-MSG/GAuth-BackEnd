package com.msg.gauth.domain.oauth.presentation.dto.response

data class OauthTokenResponseDto(
    val accessToken: String,
    val refreshToken: String
)