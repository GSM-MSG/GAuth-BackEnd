package com.msg.gauth.domain.auth.presentation.dto.request

data class PasswordInitReqDto(
    val email: String,
    val newPassword: String,
)