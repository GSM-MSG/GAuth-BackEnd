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
        val clientSecret = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "")
        val user = currentUserUtil.getCurrentUser()
        val client = clientRegisterDto.toEntity(user, clientSecret, "")
        clientRepository.save(client)
        return ClientRegisterResDto(client)
    }
}