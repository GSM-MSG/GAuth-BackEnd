package com.msg.gauth.global.util.count.auth.util

import com.msg.gauth.global.util.count.auth.MinuteSignInCount
import com.msg.gauth.global.util.count.auth.SecondSignInCount
import com.msg.gauth.domain.auth.exception.SignInMinuteCountOverException
import com.msg.gauth.domain.auth.exception.SignInSecondCountOverException
import com.msg.gauth.domain.auth.repository.MinuteSignInCountRepository
import com.msg.gauth.domain.auth.repository.SecondSignInCountRepository
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class TooManyRequestValidUtil(
    private val minuteSignInCountRepository: MinuteSignInCountRepository,
    private val secondSignInCountRepository: SecondSignInCountRepository,
    private val userRepository: UserRepository
) {
    fun validRequest(email: String){
        val secondSignInCount = (secondSignInCountRepository.findByIdOrNull(email)
            ?: secondSignInCountRepository.save(SecondSignInCount(email)))

        if (secondSignInCount.count >= 20) {
            val user = userRepository.findByEmail(email)
                ?: throw UserNotFoundException()

            userSignInBan(user)

            throw SignInSecondCountOverException()
        }
        secondSignInCount.addCount()

        secondSignInCountRepository.save(secondSignInCount)

        val minuteSignInCount = (minuteSignInCountRepository.findByIdOrNull(email)
            ?: minuteSignInCountRepository.save(MinuteSignInCount(email)))

        if (minuteSignInCount.count >= 10) {
            val user = userRepository.findByEmail(email)
                ?: throw UserNotFoundException()

            userSignInBan(user)
            throw SignInMinuteCountOverException()
        }

        minuteSignInCount.addCount()
        minuteSignInCountRepository.save(minuteSignInCount)
    }

    fun userSignInBan(user: User){
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
                state = UserState.SIGN_IN_BAN,
                profileUrl = user.profileUrl,
                wrongPasswordCount = user.wrongPasswordCount,
                oauthWrongPasswordCount = user.oauthWrongPasswordCount
            )
        )
    }
}