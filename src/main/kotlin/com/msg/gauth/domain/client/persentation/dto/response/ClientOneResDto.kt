package com.msg.gauth.domain.client.persentation.dto.response

import com.msg.gauth.domain.client.Client

data class ClientOneResDto(
    val clientId: String,
    val serviceName: String,
    val serviceUri: String,
) {
    constructor(client: Client): this(
        clientId = client.clientId,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
    )
}