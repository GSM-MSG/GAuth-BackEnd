package com.msg.gauth.domain.client.presentation.dto.request

import javax.validation.constraints.NotNull

data class AddCoworkerReqDto(
    @field:NotNull
    val userId: Long
)