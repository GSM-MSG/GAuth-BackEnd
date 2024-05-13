package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.exception.NotFoundCoworkerException
import com.msg.gauth.domain.client.exception.UserNotMatchException
import com.msg.gauth.domain.client.presentation.dto.request.DeleteCoworkerReqDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.client.repository.CoworkerRepository
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.data.repository.findByIdOrNull

@TransactionalService
class DeleteCoworkerService(
    private val userUtil: UserUtil,
    private val coworkerRepository: CoworkerRepository,
    private val clientRepository: ClientRepository,
    private val userRepository: UserRepository
) {

    fun execute(id: Long, deleteCoworkerReqDto: DeleteCoworkerReqDto) {
        val user = userUtil.fetchCurrentUser()

        val client = clientRepository.findByIdOrNull(id)
            ?: throw ClientNotFindException()

        if (user != client.createdBy)
            throw UserNotMatchException()

        val coworkerUser = userRepository.findByIdOrNull(deleteCoworkerReqDto.userId)
            ?: throw UserNotFoundException()

        val coworker = coworkerRepository.findByUserAndClient(coworkerUser, client)
            ?: throw NotFoundCoworkerException()

        coworkerRepository.delete(coworker)
    }
}