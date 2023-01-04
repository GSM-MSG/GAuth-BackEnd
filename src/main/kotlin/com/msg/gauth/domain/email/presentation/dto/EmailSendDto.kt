package com.msg.gauth.domain.email.presentation.dto

import javax.validation.constraints.Pattern

data class EmailSendDto(
    @field:Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    val email: String
)