package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.Gender
import javax.validation.constraints.NotBlank

data class AcceptTeacherReqDto(
    val id: Long,
    @field:NotBlank
    val name: String,
    val gender: Gender
)
