package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.Gender

data class AcceptStudentReqDto(
    val id: Long,
    val name: String,
    val gender: Gender,
    val grade: Int,
    val classNum: Int,
    val num: Int
)