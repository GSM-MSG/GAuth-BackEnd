package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetAcceptedUsersService(
    private val userRepository: UserRepository,
) {
    fun execute(grade: Int, classNum: Int, keyword: String, role: UserRole): List<SingleAcceptedUserResDto> =
        when(role) {
            UserRole.ROLE_STUDENT -> userRepository.search(grade, classNum, keyword)
            else -> userRepository.findAllByRolesContaining(role)
        }.map { SingleAcceptedUserResDto(it) }
}