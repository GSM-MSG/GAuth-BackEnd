package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.persentation.dto.response.SingleClientResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetAllClientsService(
    private val clientRepository: ClientRepository
) {
    fun execute(): List<SingleClientResDto> =
        clientRepository.findAll()
            .map { SingleClientResDto(it) }
}