package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.presentation.dto.response.SinglePendingListResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetPendingUsersService(
    private val userRepository: UserRepository
) {
    fun execute(): List<SinglePendingListResDto>
    = userRepository.findAllByState(UserState.PENDING)
        .map { SinglePendingListResDto(it) }
}
