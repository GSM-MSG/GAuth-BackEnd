package com.msg.gauth.domain.user.presentation.dto.response

class SingleAcceptedUserResDto(
    val id: Long,
    val name: String,
    val email: String,
    val grade: Int,
    val classNum: Int,
    val num: Int,
    val profileUrl: String?
)