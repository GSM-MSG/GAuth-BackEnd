package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.oauth.presentation.dto.response.ServiceNameResponseDto
import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.repository.ClientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetServiceNameService(
    private val clientRepository: ClientRepository,
){
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun execute(clientId: String): ServiceNameResponseDto {
        val client = (clientRepository.findByClientId(clientId)
            ?: throw ClientNotFindException())
        return ServiceNameResponseDto(
            client.serviceName
        )
    }
}