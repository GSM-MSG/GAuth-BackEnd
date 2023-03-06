package com.msg.gauth.domain.user.presentation.dto.response

import com.msg.gauth.domain.user.User

data class SinglePendingListResDto(
    val email: String,
    val profileUrl: String?
) {
    constructor(user: User): this(
        email = user.email,
         profileUrl = user.profileUrl
    )
}