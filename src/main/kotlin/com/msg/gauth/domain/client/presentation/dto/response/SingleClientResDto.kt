package com.msg.gauth.domain.client.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.enums.ServiceScope

data class SingleClientResDto @JsonCreator constructor(
    @JsonProperty("id") val id: Long,
    @JsonProperty("clientId") val clientId: String,
    @JsonProperty("serviceName") val serviceName: String,
    @JsonProperty("serviceUri") val serviceUri: String,
    @JsonProperty("serviceScope") val serviceScope: ServiceScope,
    @JsonProperty("serviceImgUrl") val serviceImgUrl: String
) {
    constructor(client: Client) : this(
        id = client.id,
        clientId = client.clientId,
        serviceName = client.serviceName,
        serviceUri = client.serviceUri,
        serviceScope = client.serviceScope,
        serviceImgUrl = client.serviceImgUrl
    )
}