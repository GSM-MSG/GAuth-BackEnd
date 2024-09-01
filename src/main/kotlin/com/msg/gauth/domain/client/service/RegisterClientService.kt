package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.presentation.dto.request.ClientRegisterReqDto
import com.msg.gauth.domain.client.presentation.dto.response.ClientRegisterResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.cache.annotation.CacheEvict
import java.util.UUID

@TransactionalService
class RegisterClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil,
) {

    @CacheEvict(value= ["Clients"], allEntries = true, cacheManager = "clientCacheManager")
    fun execute(clientRegisterDto: ClientRegisterReqDto): ClientRegisterResDto {
        val (clientSecret, clientId) = createUUID() to createUUID()

        val user = userUtil.fetchCurrentUser()

        val client = clientRegisterDto.toEntity(user, clientSecret, clientId)

        return ClientRegisterResDto(clientRepository.save(client))
    }

    private fun createUUID() = (UUID.randomUUID().toString() + UUID.randomUUID().toString())
        .replace("-", "")
}