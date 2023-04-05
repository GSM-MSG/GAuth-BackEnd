package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService
import org.springframework.data.domain.Pageable

@ReadOnlyService
class GetAcceptedUserListService(
    private val userRepository: UserRepository,
) {
    fun execute(grade: Int, classNum: Int, name: String?, pageable: Pageable): List<SingleAcceptedUserResDto> {
        val keyword: String = name ?: ""
        val userList: List<User>
        = when {
            grade == 0 && classNum == 0 && name == "" -> userRepository.findAllByStateOrderByGrade(UserState.CREATED, pageable)
            grade == 0 && classNum == 0 -> userRepository.findAllByStateAndNameContainingOrderByGrade(UserState.CREATED, keyword, pageable)
            classNum == 0 && name == "" -> userRepository.findAllByStateAndGradeOrderByGrade(UserState.CREATED, classNum, pageable)
            grade == 0 && name == ""-> userRepository.findAllByStateAndClassNumOrderByGrade(UserState.CREATED, grade, pageable)
            grade == 0 -> userRepository.findAllByStateAndClassNumAndNameContainingOrderByGrade(UserState.CREATED, classNum, keyword, pageable)
            classNum == 0 -> userRepository.findAllByStateAndGradeAndNameContainingOrderByGrade(UserState.CREATED, grade, keyword, pageable)
            name == "" -> userRepository.findAllByStateAndGradeAndClassNumOrderByGrade(UserState.CREATED, grade, classNum, pageable)
            else -> userRepository.findAllByStateAndGradeAndClassNumAndNameContainingOrderByGrade(UserState.CREATED, grade, classNum, keyword, pageable)
        }
        return userList.map {
            SingleAcceptedUserResDto(it)
        }
    }
}