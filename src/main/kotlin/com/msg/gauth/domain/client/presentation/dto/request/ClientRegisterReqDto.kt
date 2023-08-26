package com.msg.gauth.domain.client.presentation.dto.request

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.enums.ServiceScope
import com.msg.gauth.domain.user.User
import org.springframework.boot.context.properties.bind.DefaultValue
import reactor.util.annotation.NonNull
import reactor.util.annotation.Nullable
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.validation.constraints.Size

data class ClientRegisterReqDto(
    @field:Size(min = 1, max = 40)
    val serviceName: String,

    @field:Size(min = 1, max = 254)
    val serviceUri: String,

    @field:Size(min = 1, max = 254)
    val redirectUri: String,

    @Enumerated(EnumType.STRING)
    val serviceScope: ServiceScope = ServiceScope.PUBLIC
) {
    fun toEntity(user: User, clientSecret: String, clientId: String, serviceImgUrl: String): Client =
        Client(
            clientId = clientId,
            clientSecret = clientSecret,
            serviceName = serviceName,
            serviceUri = serviceUri,
            redirectUri = redirectUri,
            createdBy = user,
            serviceScope = serviceScope,
            serviceImgUrl = serviceImgUrl
        )
}