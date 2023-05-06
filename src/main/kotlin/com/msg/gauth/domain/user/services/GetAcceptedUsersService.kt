package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.presentation.dto.request.AcceptedUserRequest
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetAcceptedUsersService(
    private val userRepository: UserRepository,
) {
    fun execute(request: AcceptedUserRequest): List<SingleAcceptedUserResDto> =
        when(request.role) {
            UserRole.ROLE_STUDENT -> userRepository.search(request.grade, request.classNum, request.keyword)
            else -> userRepository.findAllByRolesContaining(request.role)
        }.map { SingleAcceptedUserResDto(it) }
}