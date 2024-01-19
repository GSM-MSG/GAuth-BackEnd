package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.presentation.dto.response.GetMyRolesResDto
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetMyRolesService(
    private val userUtil: UserUtil
) {
    // TODO: DB 이전 완료 시 roles -> userRoles.map { it.userRoleType }로 변경
    fun execute(): GetMyRolesResDto =
        GetMyRolesResDto(userUtil.fetchCurrentUser().roles)
}
