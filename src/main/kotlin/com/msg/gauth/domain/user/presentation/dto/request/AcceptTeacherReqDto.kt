package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.UserRole
import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserRoleType
import com.msg.gauth.domain.user.enums.UserState
import javax.validation.constraints.NotBlank

data class AcceptTeacherReqDto(
    val id: Long,
    @field:NotBlank
    val name: String,
    val gender: Gender
){
    fun toEntity(user: User): User =
        User(
            id = this.id,
            email = user.email,
            name = user.name,
            password = user.password,
            gender = this.gender,
            roles = mutableListOf(UserRoleType.ROLE_TEACHER),
            userRoles = user.userRoles,
            state = UserState.CREATED,
            profileUrl = user.profileUrl,
            wrongPasswordCount = user.wrongPasswordCount,
            oauthWrongPasswordCount = user.oauthWrongPasswordCount
        )
}
