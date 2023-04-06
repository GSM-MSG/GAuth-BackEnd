package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserRole
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class AcceptUserReqDto(
    @field:NotNull
    val userRole: UserRole,
    @field:NotBlank
    val name: String,
    @field:NotNull
    val gender: Gender,
    val grade: Int?,
    val classNum: Int?,
    val num: Int?
)
