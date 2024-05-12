package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.Coworker
import com.msg.gauth.domain.client.exception.AlreadyExistCoworkerException
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.exception.UserNotMatchException
import com.msg.gauth.domain.client.presentation.dto.request.AddCoworkerReqDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.client.repository.CoworkerRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.data.repository.findByIdOrNull

@TransactionalService

class AddCoworkerService(
    private val clientRepository: ClientRepository,
    private val userRepository: UserRepository,
    private val coworkerRepository: CoworkerRepository,
    private val userUtil: UserUtil,
) {
    fun execute(id: Long, addCoworkerReqDto: AddCoworkerReqDto) {
        val user = userUtil.fetchCurrentUser()

        val client = clientRepository.findByIdOrNull(id)
            ?: throw ClientNotFindException()

        if (user != client.createdBy)
            throw UserNotMatchException()

        val clientCoworker = userRepository.findByIdOrNull(addCoworkerReqDto.userId)
            ?: throw UserNotFoundException()

        if (coworkerRepository.existsByUserAndClient(clientCoworker, client))
            throw AlreadyExistCoworkerException()

        saveCoworker(clientCoworker, client)
    }

    private fun saveCoworker(user: User, client: Client) {
        val coworker = Coworker(
            user = user,
            client = client
        )
        coworkerRepository.save(coworker)
    }
}