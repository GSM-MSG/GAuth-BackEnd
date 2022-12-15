package com.msg.gauth.domain.auth.presentation.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class PasswordInitReqDto(
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    val email: String,
    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&])[A-Za-z[0-9]\$@\$!%*#?&]{8,20}$")
    val newPassword: String,
)