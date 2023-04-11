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
    fun execute(grade: Int, classNum: Int, keyword: String, page: Int, size: Int): Page<SingleAcceptedUserResDto> =
        userRepository.findAllByState(UserState.CREATED, PageRequest.of(page, size))
            .let { filterUser(it, grade, classNum, keyword) }
            .map { SingleAcceptedUserResDto(it) }

    private fun filterUser(users: Page<User>, grade: Int, classNum: Int, keyword: String): Page<User> =
        users.filter { grade == 0 || it.grade == grade }
            .filter { classNum == 0 || it.classNum == classNum }
            .filter { keyword.isEmpty() || it.name!!.contains(keyword) }
            .toList()
            .let { PageImpl(it, users.pageable, it.size.toLong()) }

}