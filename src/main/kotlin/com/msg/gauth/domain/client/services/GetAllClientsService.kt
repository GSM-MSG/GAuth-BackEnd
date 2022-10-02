package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.client.persentation.dto.response.ClientOneResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.util.CurrentUserUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetAllClientsService(
    private val clientRepository: ClientRepository,
    private val currentUserUtil: CurrentUserUtil,
) {
    fun execute(): List<ClientOneResDto> =
        clientRepository.findAllByCreatedBy(currentUserUtil.getCurrentUser())
            .map { ClientOneResDto(it) }
}