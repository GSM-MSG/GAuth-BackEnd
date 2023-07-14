package com.msg.gauth.domain.client.presentation.dto.response

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.enums.ServiceScope

data class ClientResDto(
    val clientId: Long,
    val serviceName: String,
    val serviceUri: String,
    val ownerName: String,
    val serviceScope: ServiceScope
) {

    constructor(client: Client): this(
        clientId = client.id,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
        ownerName = client.createdBy.name ?: "",
        serviceScope = client.serviceScope
    )
}