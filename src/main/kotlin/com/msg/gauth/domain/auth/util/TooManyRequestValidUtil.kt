package com.msg.gauth.domain.auth.util

import com.msg.gauth.domain.auth.MinuteSignInCount
import com.msg.gauth.domain.auth.SecondSignInCount
import com.msg.gauth.domain.auth.exception.SignInMinuteCountOverException
import com.msg.gauth.domain.auth.exception.SignInSecondCountOverException
import com.msg.gauth.domain.auth.repository.MinuteSignInCountRepository
import com.msg.gauth.domain.auth.repository.SecondSignInCountRepository
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
            userRepository.save(user.updateUserState(UserState.SIGN_IN_BAN))
            throw SignInSecondCountOverException()
        }
        secondSignInCount.addCount()
        secondSignInCountRepository.save(secondSignInCount)
        val minuteSignInCount = (minuteSignInCountRepository.findByIdOrNull(email)
            ?: minuteSignInCountRepository.save(MinuteSignInCount(email)))
        if (minuteSignInCount.count >= 10) {
            val user = userRepository.findByEmail(email)
                ?: throw UserNotFoundException()
            userRepository.save(user.updateUserState(UserState.SIGN_IN_BAN))
            throw SignInMinuteCountOverException()
        }
        minuteSignInCount.addCount()
        minuteSignInCountRepository.save(minuteSignInCount)
    }
}