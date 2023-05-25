package com.msg.gauth.domain.client.service

import com.msg.gauth.domain.client.presentation.dto.response.ClientResDto
import com.msg.gauth.domain.client.repository.ClientRepository
import com.msg.gauth.global.annotation.service.ReadOnlyService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest

@ReadOnlyService
class GetClientsByServiceNameService(
    private val clientRepository: ClientRepository,
) {

    fun execute(page: Int, size: Int, serviceName: String): Page<ClientResDto> =
        clientRepository.findByServiceNameContaining(serviceName, PageRequest.of(page, size))
            .map { ClientResDto(it) }
}