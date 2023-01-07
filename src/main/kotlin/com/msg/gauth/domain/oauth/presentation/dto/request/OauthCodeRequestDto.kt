package com.msg.gauth.domain.oauth.presentation.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class OauthCodeRequestDto(
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    val email: String,

    @field:NotBlank
    val password: String,

    @field:NotBlank
    val redirectUri: String,
)