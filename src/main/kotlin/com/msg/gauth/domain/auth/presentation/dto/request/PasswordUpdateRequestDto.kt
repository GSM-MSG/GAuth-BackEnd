package com.msg.gauth.domain.auth.presentation.dto.request

import com.msg.gauth.domain.user.User
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class PasswordUpdateRequestDto(
    @field:NotBlank
    val password: String,
    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#\$%^*+=-?<>])(?=.*[0-9]).{8,20}\$")
    val newPassword: String
)