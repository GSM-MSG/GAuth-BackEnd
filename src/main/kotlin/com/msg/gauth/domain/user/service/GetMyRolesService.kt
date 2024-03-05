package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.presentation.dto.response.GetMyRolesResDto
import com.msg.gauth.domain.user.repository.UserRoleRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetMyRolesService(
    private val userUtil: UserUtil,
    private val userRoleRepository: UserRoleRepository
) {
    fun execute(): GetMyRolesResDto {
        val user = userUtil.fetchCurrentUser()
        val userRole = userRoleRepository.findByUser(user)
        return GetMyRolesResDto(userRole.map { it.userRoleType })
    }
}
