package com.msg.gauth.domain.client.services

import com.msg.gauth.domain.admin.presentation.dto.ClientResDto
import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@ReadOnlyService
class GetClientsByServiceNameService(
    private val clientRepository: ClientRepository,
) {

    fun execute(pageable: Pageable, serviceName: String): Page<ClientResDto> =
        clientRepository.findByServiceNameContaining(serviceName, pageable)
            .map { ClientResDto(it) }
}