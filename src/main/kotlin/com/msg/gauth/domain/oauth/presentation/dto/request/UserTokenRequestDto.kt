package com.msg.gauth.domain.oauth.presentation.dto.request

data class UserTokenRequestDto(
    val code: String,
    val clientId: String,
    val clientSecret: String,
)