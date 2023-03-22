package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class AcceptedUserService(
    private val userRepository: UserRepository,
) {
    fun execute(grade: Int, classNum: Int, name: String): List<SingleAcceptedUserResDto> {
        var userList = userRepository.findByStateAndGradeAndClassNumAndNameContaining(UserState.CREATED, grade, classNum, name)
        return userList.map {
            SingleAcceptedUserResDto(it)
        }
    }
}