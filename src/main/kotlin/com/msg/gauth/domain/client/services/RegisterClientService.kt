package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.persentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.persentation.dto.response.ClientRegisterResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class RegisterClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil
) {
    @Transactional(rollbackFor = [Exception::class])
    fun execute(clientRegisterDto: ClientRegisterReqDto): ClientRegisterResDto {
        val clientSecret = createUUID()
        val clientId = createUUID()
        val user = userUtil.fetchCurrentUser()
        val client = clientRegisterDto.toEntity(user, clientSecret, clientId)
        return ClientRegisterResDto(clientRepository.save(client))
    }

    private fun createUUID() = (UUID.randomUUID().toString() + UUID.randomUUID().toString()).replace("-", "")
}