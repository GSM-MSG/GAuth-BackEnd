package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.Gender

data class AcceptTeacherReqDto(
    val id: Long,
    val name: String,
    val gender: Gender
)
