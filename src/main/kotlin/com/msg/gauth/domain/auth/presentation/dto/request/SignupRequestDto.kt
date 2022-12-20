package com.msg.gauth.domain.auth.presentation.dto.request

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignUpDto(
    @field:NotBlank
    @field:Pattern(regexp = "^[a-zA-Z0-9]+@gsm.hs.kr$")
    val email: String,

    @field:NotBlank
    @field:Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&])[A-Za-z[0-9]\$@\$!%*#?&]{8,20}$")
    val password: String,

    val profileUrl: String?,
) {
    fun toEntity(password: String): User =
        User(
            email = email,
            password = password,
            roles = mutableListOf(UserRole.ROLE_STUDENT),
            state = UserState.PENDING,
            profileUrl = profileUrl,
        )
}