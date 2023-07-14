package com.msg.gauth.domain.client.presentation.dto.response

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.enums.ServiceScope

data class SingleClientResDto(
    val id: Long,
    val clientId: String,
    val serviceName: String,
    val serviceUri: String,
    val serviceScope: ServiceScope
) {
    constructor(client: Client): this(
        id = client.id,
        clientId = client.clientId,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
        serviceScope = client.serviceScope
    )
}