package com.msg.gauth.domain.user.presentation.dto.response

import com.msg.gauth.domain.user.User

data class SingleAcceptedUserResDto(
    val id: Long,
    val name: String,
    val email: String,
    val grade: Int?,
    val classNum: Int?,
    val num: Int?,
    val profileUrl: String?
) {

    constructor(user: User) : this(
        user.id,
        user.name!!,
        user.email,
        user.grade,
        user.classNum,
        user.num,
        user.profileUrl
    )
}