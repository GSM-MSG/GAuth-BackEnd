package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.enums.ServiceScope
import com.msg.gauth.domain.client.presentation.dto.response.SingleClientResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetAllClientsService(
    private val clientRepository: ClientRepository
) {

    fun execute(): List<SingleClientResDto> =
        clientRepository.findByServiceScope(ServiceScope.PUBLIC)
            .map { SingleClientResDto(it) }
}