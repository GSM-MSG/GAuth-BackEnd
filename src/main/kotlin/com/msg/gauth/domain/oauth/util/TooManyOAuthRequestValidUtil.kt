package com.msg.gauth.domain.oauth.util

import com.msg.gauth.domain.oauth.MinuteOAuthSignInCount
import com.msg.gauth.domain.oauth.SecondOAuthSignInCount
import com.msg.gauth.domain.oauth.exception.OAuthSignInMinuteOverException
import com.msg.gauth.domain.oauth.exception.OAuthSignInSecondOverException
import com.msg.gauth.domain.oauth.repository.MinuteOAuthSignInCountRepository
import com.msg.gauth.domain.oauth.repository.SecondOAuthSignInCountRepository
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
            userRepository.save(user.updateUserState(UserState.OAUTH_BAN))
            throw OAuthSignInSecondOverException()
        }
        secondSignInCount.addCount()
        secondSignInCountRepository.save(secondSignInCount)
        val minuteSignInCount = (minuteSignInCountRepository.findByIdOrNull(email)
            ?: minuteSignInCountRepository.save(MinuteOAuthSignInCount(email)))
        if (minuteSignInCount.count >= 10) {
            val user = userRepository.findByEmail(email)
                ?: throw UserNotFoundException()
            userRepository.save(user.updateUserState(UserState.OAUTH_BAN))
            throw OAuthSignInMinuteOverException()
        }
        minuteSignInCount.addCount()
        minuteSignInCountRepository.save(minuteSignInCount)
    }
}