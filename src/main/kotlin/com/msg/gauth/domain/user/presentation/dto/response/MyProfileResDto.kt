package com.msg.gauth.domain.user.presentation.dto.response

import com.msg.gauth.domain.client.Client
import com.msg.gauth.domain.client.enums.ServiceScope
import com.msg.gauth.domain.user.User

data class MyProfileResDto(
    val email: String,
    val name: String?,
    val grade: Int?,
    val classNum: Int?,
    val number: Int?,
    val profileUrl: String?,
    val clientList: List<SingleClientResDto>
) {
    constructor(user: User, clientList: List<SingleClientResDto>): this(
        user.email, user.name, user.grade, user.classNum, user.num, user.profileUrl, clientList
    )
    data class SingleClientResDto(
        val id: Long,
        val clientId: String,
        val serviceName: String,
        val serviceUri: String,
        val serviceScope: ServiceScope,
        val serviceImgUrl: String
    ) {
        constructor(client: Client): this(
            id = client.id,
            clientId = client.clientId,
            serviceName = client.serviceName,
            serviceUri = client.serviceUri,
            serviceScope = client.serviceScope,
            serviceImgUrl = client.serviceImgUrl
        )
    }
}