package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.User
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class PasswordChangeReqDto(
    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#\$%^*+=-?<>])(?=.*[0-9]).{8,20}\$")
    val password: String,
){

    fun toEntity(user: User, newPassword: String): User{
        return User(
            id = user.id,
            email = user.email,
            password = newPassword,
            gender = user.gender,
            name = user.name,
            grade = user.grade,
            classNum = user.grade,
            num = user.num,
            roles = user.roles,
            userRoles = user.userRoles,
            state = user.state,
            profileUrl = user.profileUrl,
            wrongPasswordCount = user.wrongPasswordCount,
            oauthWrongPasswordCount = user.oauthWrongPasswordCount
        )
    }
}