package com.msg.gauth.domain.oauth.presentation.dto.request

import javax.validation.constraints.NotBlank

data class UserTokenRequestDto(
    @field:NotBlank
    val code: String,

    @field:NotBlank
    val clientId: String,

    @field:NotBlank
    val clientSecret: String,

    @field:NotBlank
    val redirectUri: String,
)