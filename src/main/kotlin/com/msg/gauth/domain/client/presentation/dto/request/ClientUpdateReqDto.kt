package com.msg.gauth.domain.client.presentation.dto.request

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.enums.ServiceScope
import reactor.util.annotation.Nullable
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class ClientUpdateReqDto(
    @field:Size(min = 1, max = 40)
    val serviceName: String,

    @field:Size(min = 1, max = 254)
    val serviceUri: String,

    @field:Size(min = 1, max = 254)
    val redirectUri: String,

    @Enumerated(EnumType.STRING)
    val serviceScope: ServiceScope = ServiceScope.PUBLIC,

    @field:Nullable
    val serviceImgUrl: String = ""
) {

    fun toEntity(client: Client): Client =
        Client(
            id = client.id,
            clientId = client.clientId,
            clientSecret = client.clientSecret,
            redirectUri = redirectUri,
            serviceName = serviceName,
            serviceUri = serviceUri,
            createdBy = client.createdBy,
            serviceScope = serviceScope,
            serviceImgUrl = serviceImgUrl
        )
}