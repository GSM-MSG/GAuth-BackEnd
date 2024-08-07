package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserRoleType
import com.msg.gauth.domain.user.enums.UserState
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class AcceptUserReqDto(
    @field:NotNull
    val userRoleType: UserRoleType,
    @field:NotBlank
    val name: String,
    @field:NotNull
    val gender: Gender,
    val grade: Int?,
    val classNum: Int?,
    val num: Int?
) {

    fun toEntity(user: User): User {
        return User(
            id = user.id,
            email = user.email,
            password = user.password,
            gender = gender,
            name = name,
            grade = grade,
            classNum = classNum,
            num = num,
            state = UserState.CREATED,
            profileUrl = user.profileUrl,
            wrongPasswordCount = user.wrongPasswordCount,
            oauthWrongPasswordCount = user.oauthWrongPasswordCount
        )
    }
}