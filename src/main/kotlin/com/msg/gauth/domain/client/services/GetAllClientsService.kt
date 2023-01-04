package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.persentation.dto.response.SingleClientResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.domain.user.utils.UserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
class GetAllClientsService(
    private val clientRepository: ClientRepository
) {
    fun execute(): List<SingleClientResDto> =
        clientRepository.findAll()
            .map { SingleClientResDto(it) }
}