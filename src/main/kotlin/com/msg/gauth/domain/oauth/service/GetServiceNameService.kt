package com.msg.gauth.domain.oauth.service

import com.msg.gauth.domain.oauth.presentation.dto.response.ServiceNameResponseDto
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService

@ReadOnlyService
class GetServiceNameService(
    private val clientRepository: ClientRepository,
) {

    fun execute(clientId: String): ServiceNameResponseDto {
        val client = (
            clientRepository.findByClientId(clientId)
                ?: throw ClientNotFindException()
            )

        return ServiceNameResponseDto(
            client.serviceName
        )
    }
}