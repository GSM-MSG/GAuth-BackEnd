package com.msg.gauth.domain.user.presentation.dto.response

import com.msg.gauth.domain.user.enums.UserRoleType

data class GetMyRolesResDto(
    val roles: List<UserRoleType>
)
