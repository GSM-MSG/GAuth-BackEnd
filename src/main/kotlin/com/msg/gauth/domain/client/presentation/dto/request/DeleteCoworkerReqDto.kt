package com.msg.gauth.domain.client.presentation.dto.request

import javax.validation.constraints.NotNull

data class DeleteCoworkerReqDto(
    @field:NotNull
    val userId: Long
)