package com.msg.gauth.domain.client.persentation.dto.response

import com.msg.gauth.domain.client.Client

data class SingleClientResDto(
    val id: Long,
    val clientId: String,
    val serviceName: String,
    val serviceUri: String,
) {
    constructor(client: Client): this(
        id = client.id,
        clientId = client.clientId,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
    )
}