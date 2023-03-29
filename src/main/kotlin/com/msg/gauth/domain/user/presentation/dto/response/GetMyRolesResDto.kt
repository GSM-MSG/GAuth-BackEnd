package com.msg.gauth.domain.user.presentation.dto.response

import com.msg.gauth.domain.user.enums.UserRole

data class GetMyRolesResDto(
    val roles: List<UserRole>
)
