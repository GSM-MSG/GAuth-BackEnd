package com.msg.gauth.domain.auth.presentation.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class SignUpV2RequestDto (
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9.]+@gsm.hs.kr$")
    val email: String,

    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#\$%^*+=-?<>])(?=.*[0-9]).{8,20}\$")
    val password: String,

    @field:NotNull
    val grade: Int,

    @field:NotNull
    val classNum: Int,

    @field:NotNull
    val num: Int,

    @field:NotBlank
    val name: String,

    val profileUrl: String?,
)