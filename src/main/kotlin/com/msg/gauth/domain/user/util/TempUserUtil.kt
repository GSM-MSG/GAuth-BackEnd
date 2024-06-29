package com.msg.gauth.domain.user.util

import com.msg.gauth.global.util.count.auth.TempSignInBan
import com.msg.gauth.domain.auth.exception.SignInBanException
import com.msg.gauth.domain.auth.exception.TempSignInBanException
import com.msg.gauth.domain.auth.repository.TempSignInBanRepository
import com.msg.gauth.global.util.count.oauth.TempOAuthSignInBan
import com.msg.gauth.domain.oauth.repository.TempOAuthSignInBanRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class TempUserUtil(
    private val userRepository: UserRepository,
    private val tempSignInBanRepository: TempSignInBanRepository,
    private val tempOAuthSignInBanRepository: TempOAuthSignInBanRepository
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
                    state = user.state,
                    profileUrl = user.profileUrl,
                    wrongPasswordCount = 0,
                    oauthWrongPasswordCount = user.oauthWrongPasswordCount
                )
            )
        }
    }

    fun validOAuthWrongCount(user: User) {
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
                state = user.state,
                profileUrl = user.profileUrl,
                wrongPasswordCount = user.wrongPasswordCount,
                oauthWrongPasswordCount = user.oauthWrongPasswordCount + 1
            )
        )

        if (updatedUser.oauthWrongPasswordCount >= 5) {
            tempOAuthSignInBanRepository.save(TempOAuthSignInBan(user.email))
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
                    state = user.state,
                    profileUrl = user.profileUrl,
                    wrongPasswordCount = user.wrongPasswordCount,
                    oauthWrongPasswordCount = 0
                )
            )
        }
    }

    fun isUserBan(user: User) {
        when {
            user.state == UserState.SIGN_IN_BAN -> throw SignInBanException()
            tempOAuthSignInBanRepository.existsById(user.email) -> throw TempSignInBanException()
        }
    }

    fun isUserOAuthBan(user: User) {
        when {
            user.state == UserState.OAUTH_BAN -> throw SignInBanException()
            tempOAuthSignInBanRepository.existsById(user.email) -> throw TempSignInBanException()
        }
    }

    fun resetOAuthWrongPasswordCount(user: User) {
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
                state = user.state,
                profileUrl = user.profileUrl,
                wrongPasswordCount = user.wrongPasswordCount,
                oauthWrongPasswordCount = 0
            )
        )
    }

    fun resetWrongPasswordCount(user: User) {
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
                state = user.state,
                profileUrl = user.profileUrl,
                wrongPasswordCount = 0,
                oauthWrongPasswordCount = user.oauthWrongPasswordCount
            )
        )
    }
}