package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.Gender
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class AcceptStudentReqDto(
    @field: NotNull
    val id: Long,
    @field: NotBlank
    val name: String,
    val gender: Gender,
    @field: NotNull
    val grade: Int,
    @field: NotNull
    val classNum: Int,
    @field: NotNull
    val num: Int
)