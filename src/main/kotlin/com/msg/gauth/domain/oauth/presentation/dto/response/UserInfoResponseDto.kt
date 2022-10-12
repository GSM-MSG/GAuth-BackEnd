package com.msg.gauth.domain.oauth.presentation.dto.response

import com.msg.gauth.domain.user.enums.Gender

class UserInfoResponseDto(
    val email: String,
    val grade: Int?,
    val classNum: Int?,
    val num: Int?,
    val gender: Gender?,
)