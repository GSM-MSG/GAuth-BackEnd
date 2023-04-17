package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@ReadOnlyService
class GetAcceptedUsersService(
    private val userRepository: UserRepository,
) {
    fun execute(grade: Int, classNum: Int, keyword: String): List<SingleAcceptedUserResDto> =
        userRepository.searchUser(grade, classNum, keyword)
            .map { SingleAcceptedUserResDto(it) }
}