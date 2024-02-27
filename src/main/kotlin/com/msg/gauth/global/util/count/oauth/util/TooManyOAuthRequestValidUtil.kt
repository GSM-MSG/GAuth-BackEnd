package com.msg.gauth.global.util.count.oauth.util

import com.msg.gauth.global.util.count.oauth.MinuteOAuthSignInCount
import com.msg.gauth.global.util.count.oauth.SecondOAuthSignInCount
import com.msg.gauth.domain.oauth.exception.OAuthSignInMinuteOverException
import com.msg.gauth.domain.oauth.exception.OAuthSignInSecondOverException
import com.msg.gauth.domain.oauth.repository.MinuteOAuthSignInCountRepository
import com.msg.gauth.domain.oauth.repository.SecondOAuthSignInCountRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TooManyOAuthRequestValidUtil(
    private val minuteSignInCountRepository: MinuteOAuthSignInCountRepository,
    private val secondSignInCountRepository: SecondOAuthSignInCountRepository,
    private val userRepository: UserRepository
) {
    fun validRequest(email: String){
        val secondSignInCount = (secondSignInCountRepository.findByIdOrNull(email)
            ?: secondSignInCountRepository.save(SecondOAuthSignInCount(email)))

        if (secondSignInCount.count >= 20) {
            val user = userRepository.findByEmail(email)
                ?: throw UserNotFoundException()

            userOAuthBan(user)

            throw OAuthSignInSecondOverException()
        }

        secondSignInCount.addCount()
        secondSignInCountRepository.save(secondSignInCount)

        val minuteSignInCount = (minuteSignInCountRepository.findByIdOrNull(email)
            ?: minuteSignInCountRepository.save(MinuteOAuthSignInCount(email)))

        if (minuteSignInCount.count >= 10) {
            val user = userRepository.findByEmail(email)
                ?: throw UserNotFoundException()

            userOAuthBan(user)

            throw OAuthSignInMinuteOverException()
        }

        minuteSignInCount.addCount()
        minuteSignInCountRepository.save(minuteSignInCount)
    }

    fun userOAuthBan(user: User){
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
                userRoles = user.userRoles,
                state = UserState.OAUTH_BAN,
                profileUrl = user.profileUrl,
                wrongPasswordCount = user.wrongPasswordCount,
                oauthWrongPasswordCount = user.oauthWrongPasswordCount
            )
        )
    }
}