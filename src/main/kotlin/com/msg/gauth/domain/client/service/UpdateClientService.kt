package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.exception.ClientNotFindException
import com.msg.gauth.domain.client.presentation.dto.request.ClientUpdateReqDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.cache.annotation.CacheEvict

@TransactionalService
class UpdateClientService(
    private val clientRepository: ClientRepository,
    private val userUtil: UserUtil
) {

    @CacheEvict(value = ["Clients"], allEntries = true, cacheManager = "redisCacheManager")
    fun updateClient(id: Long, clientUpdateReqDto: ClientUpdateReqDto) {
        val client = clientRepository.findByIdAndCreatedBy(id, userUtil.fetchCurrentUser())
            ?: throw ClientNotFindException()

        clientRepository.save(clientUpdateReqDto.toEntity(client))
    }
}