package com.msg.gauth.domain.user.service

import com.msg.gauth.domain.user.UserRole
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.presentation.dto.request.AcceptUserReqDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.repository.UserRoleRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.cache.annotation.CacheEvict

@TransactionalService
class AcceptUserSignUpService(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository
) {

    @CacheEvict(value = ["AcceptedUsers"], allEntries = true, cacheManager = "redisCacheManager")
    fun execute(id: Long, acceptUserReqDto: AcceptUserReqDto) {
        val user = userRepository.findByIdAndState(id, UserState.PENDING)
            ?: throw UserNotFoundException()

        val signedUpUser = acceptUserReqDto.toEntity(user)

        val userRole = UserRole(
            user = signedUpUser,
            userRoleType = acceptUserReqDto.userRoleType
        )

        userRoleRepository.save(userRole)
        userRepository.save(signedUpUser)
    }
}