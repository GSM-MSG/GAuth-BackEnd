package com.msg.gauth.domain.admin.presentation.dto

import com.msg.gauth.domain.client.Client

data class ClientResDto(
    val clientId: Long,
    val serviceName: String,
    val serviceUri: String,
    val ownerName: String?
) {

    constructor(client: Client): this(
        clientId = client.id,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
        ownerName = client.createdBy.name
    )
}