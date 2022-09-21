package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.persentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.persentation.dto.response.ClientRegisterResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.util.CurrentUserUtil
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class RegisterClientService(
    private val clientRepository: ClientRepository,
    private val currentUserUtil: CurrentUserUtil,
){
    fun execute(clientRegisterDto: ClientRegisterReqDto): ClientRegisterResDto {
        val clientSecret = createUUID()
        val clientId = createUUID()
        val user = currentUserUtil.getCurrentUser()
        val client = clientRegisterDto.toEntity(user, clientSecret, clientId)
        return ClientRegisterResDto(clientRepository.save(client))
    }

    private fun createUUID() = (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-", "")
}