package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class AcceptedUserService(
    private val userRepository: UserRepository,
) {
    fun execute(grade: Int, classNum: Int, name: String): List<SingleAcceptedUserResDto> {
        val userList: List<User>
        = when {
            grade == 0 && classNum == 0 && name == "" -> userRepository.findAllByState(UserState.CREATED)

            grade == 0 && classNum == 0 -> userRepository.findAllByStateAndNameContaining(UserState.CREATED, name)
            classNum == 0 && name == "" -> userRepository.findAllByStateAndClassNum(UserState.CREATED, classNum)
            grade == 0 && name == ""-> userRepository.findAllByStateAndGrade(UserState.CREATED, grade)

            grade == 0 -> userRepository.findAllByStateAndClassNumAndNameContaining(UserState.CREATED, classNum, name)
            classNum == 0 -> userRepository.findAllByStateAndGradeAndNameContaining(UserState.CREATED, grade, name)
            name == "" -> userRepository.findAllByStateAndGradeAndClassNum(UserState.CREATED, grade, classNum)

            else -> userRepository.findAllByStateAndGradeAndClassNumAndNameContaining(UserState.CREATED, grade, classNum, name)
        }
        return userList.map {
            SingleAcceptedUserResDto(it)
        }
    }
}