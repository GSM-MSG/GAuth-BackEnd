package com.msg.gauth.domain.client.presentation.dto.response

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.enums.ServiceScope

data class ClientRegisterResDto(
    val clientId: String,
    val clientSecret: String,
    val redirectUri: String,
    val serviceName: String,
    val serviceUri: String,
    val serviceScope: ServiceScope
) {
    constructor(client: Client): this(
        clientId = client.clientId,
        clientSecret = client.clientSecret,
        redirectUri = client.redirectUri,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
        serviceScope = client.serviceScope
    )
}