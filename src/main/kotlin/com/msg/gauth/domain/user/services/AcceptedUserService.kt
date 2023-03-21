package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class AcceptedUserService(
    private val userRepository: UserRepository,
) {
    fun execute(grade: Int, classNum: Int, keyword: String): List<SingleAcceptedUserResDto> {
        var userList = userRepository.findAllByState(UserState.CREATED)
        if(grade != 0)
            userList = userList.filter { it.grade == grade }
        if(classNum != 0)
            userList = userList.filter { it.classNum == classNum }
        if(keyword != "0")
            userList = userList.filter { it.name!!.contains(keyword) }

        return userList.map { SingleAcceptedUserResDto(it) }

    }
}