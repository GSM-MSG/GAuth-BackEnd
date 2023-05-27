package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class AcceptUserReqDto(
    @field:NotNull
    val userRole: UserRole,
    @field:NotBlank
    val name: String,
    @field:NotNull
    val gender: Gender,
    val grade: Int?,
    val classNum: Int?,
    val num: Int?
) {
    fun toStudentEntity(user: User): User {
        return User(
            id = user.id,
            email = user.email,
            password = user.password,
            gender = user.gender,
            name = name,
            grade = grade,
            classNum = classNum,
            num = num,
            roles = mutableListOf(UserRole.ROLE_STUDENT),
            state = UserState.CREATED,
            profileUrl = user.profileUrl
        )
    }

    fun toTeacherEntity(user: User): User{
        return User(
            id = user.id,
            email = user.email,
            password = user.password,
            gender = user.gender,
            name = name,
            grade = grade,
            classNum = classNum,
            num = num,
            roles = mutableListOf(UserRole.ROLE_TEACHER),
            state = UserState.CREATED,
            profileUrl = user.profileUrl
        )
    }

    fun toGraduateEntity(user: User): User{
        return User(
            id = user.id,
            email = user.email,
            password = user.password,
            gender = user.gender,
            name = name,
            grade = grade,
            classNum = classNum,
            num = num,
            roles = mutableListOf(UserRole.ROLE_GRADUATE),
            state = UserState.CREATED,
            profileUrl = user.profileUrl
        )
    }
}
