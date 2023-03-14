package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.Gender
import javax.validation.constraints.NotBlank

data class AcceptStudentReqDto(
    @field: NotBlank
    val id: Long,
    @field: NotBlank
    val name: String,
    @field: NotBlank
    val gender: Gender,
    @field: NotBlank
    val grade: Int,
    @field: NotBlank
    val classNum: Int,
    @field: NotBlank
    val num: Int
)