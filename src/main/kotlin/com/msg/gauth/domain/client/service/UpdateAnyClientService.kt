package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.presentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.data.repository.findByIdOrNull

@TransactionalService
class UpdateAnyClientService(
    private val clientRepository: ClientRepository
) {

    fun execute(id: Long, clientUpdateReqDto: ClientUpdateReqDto) {
        val client: Client = clientRepository.findByIdOrNull(id)
            ?: throw ClientNotFindException()
        clientRepository.save(clientUpdateReqDto.toEntity(client))
    }

}