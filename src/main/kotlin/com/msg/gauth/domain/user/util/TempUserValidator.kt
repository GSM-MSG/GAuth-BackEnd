package com.msg.gauth.domain.user.util

import com.msg.gauth.domain.auth.TempSignInBan
import com.msg.gauth.domain.auth.exception.SignInBanException
import com.msg.gauth.domain.auth.exception.TempSignInBanException
import com.msg.gauth.domain.auth.repository.TempSignInBanRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class TempUserValidator(
    private val userRepository: UserRepository,
    private val tempSignInBanRepository: TempSignInBanRepository
) {

    fun validWrongCount(user: User) {
        val updatedUser = userRepository.save(
            User(
                id = user.id,
                email = user.email,
                password = user.password,
                gender = user.gender,
                name = user.name,
                grade = user.grade,
                classNum = user.classNum,
                num = user.num,
                roles = user.roles,
                state = user.state,
                profileUrl = user.profileUrl,
                wrongPasswordCount = user.wrongPasswordCount + 1,
                oauthWrongPasswordCount = user.oauthWrongPasswordCount
            )
        )
        if (updatedUser.wrongPasswordCount >= 5) {
            tempSignInBanRepository.save(TempSignInBan(user.email))
            userRepository.save(
                User(
                    id = user.id,
                    email = user.email,
                    password = user.password,
                    gender = user.gender,
                    name = user.name,
                    grade = user.grade,
                    classNum = user.classNum,
                    num = user.num,
                    roles = user.roles,
                    state = user.state,
                    profileUrl = user.profileUrl,
                    wrongPasswordCount = 0,
                    oauthWrongPasswordCount = user.oauthWrongPasswordCount
                )
            )
        }
    }

    fun isUserBan(user: User) {
        when {
            user.state == UserState.SIGN_IN_BAN -> throw SignInBanException()
            tempSignInBanRepository.existsById(user.email) -> throw TempSignInBanException()
        }
    }
}