package com.msg.gauth.domain.user.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.msg.gauth.domain.user.User

data class SingleAcceptedUserResDto @JsonCreator constructor(
    @JsonProperty("id") val id: Long,
    @JsonProperty("name") val name: String,
    @JsonProperty("email") val email: String,
    @JsonProperty("grade") val grade: Int?,
    @JsonProperty("classNum") val classNum: Int?,
    @JsonProperty("num") val num: Int?,
    @JsonProperty("profileUrl") val profileUrl: String?
) {

    constructor(user: User) : this(
        user.id,
        user.name!!,
        user.email,
        user.grade,
        user.classNum,
        user.num,
        user.profileUrl
    )
}