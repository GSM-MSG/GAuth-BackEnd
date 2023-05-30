package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserState
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class AcceptStudentReqDto(
    @field: NotNull
    val id: Long,
    @field: NotBlank
    val name: String,
    val gender: Gender,
    @field: NotNull
    val grade: Int,
    @field: NotNull
    val classNum: Int,
    @field: NotNull
    val num: Int
) {

    fun toEntity(user: User): User =
        User(
            id = this.id,
            email = user.email,
            password = user.password,
            gender = user.gender,
            name = this.name,
            grade = this.grade,
            classNum = this.classNum,
            num = this.num,
            roles = user.roles,
            state = UserState.CREATED,
            profileUrl = user.profileUrl,
            wrongPasswordCount = user.wrongPasswordCount,
            oauthWrongPasswordCount = user.oauthWrongPasswordCount
        )


}