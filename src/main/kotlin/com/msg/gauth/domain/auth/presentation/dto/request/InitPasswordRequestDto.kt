package com.msg.gauth.domain.auth.presentation.dto.request

import com.msg.gauth.domain.user.User
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class InitPasswordRequestDto(
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9.]+@gsm.hs.kr$")
    val email: String,
    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#\$%^*+=-?<>])(?=.*[0-9]).{8,20}\$")
    val newPassword: String,
) {

    fun toEntity(user: User, encodedPassword: String): User =
        User(
            id = user.id,
            email = user.email,
            password = encodedPassword,
            gender = user.gender,
            name = user.name,
            grade = user.grade,
            classNum = user.classNum,
            num = user.num,
            state = user.state,
            profileUrl = user.profileUrl
        )
}