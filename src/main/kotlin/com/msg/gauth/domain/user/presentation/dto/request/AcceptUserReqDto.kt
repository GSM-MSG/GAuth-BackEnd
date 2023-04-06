package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserRole

data class AcceptUserReqDto(
    val id: Long,
    val userRole: UserRole,
    val name: String,
    val gender: Gender,
    val grade: Int?,
    val classNum: Int?,
    val num: Int?
)
