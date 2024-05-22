package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.exception.UserNotMatchException
import com.msg.gauth.domain.client.presentation.dto.response.GetCoworkerResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.client.repository.CoworkerRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.ReadOnlyService
import org.springframework.data.repository.findByIdOrNull

@ReadOnlyService
class GetCoworkerService(
    private val userUtil: UserUtil,
    private val clientRepository: ClientRepository,
    private val coworkerRepository: CoworkerRepository
) {

    fun execute(id: Long): List<GetCoworkerResDto> {
        val user = userUtil.fetchCurrentUser()

        val client = clientRepository.findByIdOrNull(id)
            ?: throw ClientNotFindException()

        if (user != client.createdBy)
            throw UserNotMatchException()

        val serviceCoworkers = coworkerRepository.findByClient(client)

        val response = serviceCoworkers.map { GetCoworkerResDto(it.user.id) }

        return response
    }

}