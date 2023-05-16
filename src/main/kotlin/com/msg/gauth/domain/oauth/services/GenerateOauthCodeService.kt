package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.auth.exception.SignInBanException
import com.msg.gauth.domain.oauth.OauthCode
import com.msg.gauth.domain.oauth.TempOAuthSignInBan
import com.msg.gauth.domain.oauth.exception.TempOAuthSignInBanException
import com.msg.gauth.domain.oauth.exception.UserStatePendingException
import com.msg.gauth.domain.oauth.presentation.dto.request.OauthCodeRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthCodeResponseDto
import com.msg.gauth.domain.oauth.repository.OauthCodeRepository
import com.msg.gauth.domain.oauth.repository.TempOAuthSignInBanRepository
import com.msg.gauth.domain.oauth.util.TooManyOAuthRequestValidUtil
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.utils.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@TransactionalService(noRollbackFor = [PasswordMismatchException::class])
class GenerateOauthCodeService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val oauthCodeRepository: OauthCodeRepository,
    private val tempOAuthSignInBanRepository: TempOAuthSignInBanRepository,
    private val tooManyOAuthRequestValidUtil: TooManyOAuthRequestValidUtil,
    private val userUtil: UserUtil
){
    fun execute(oauthLoginRequestDto: OauthCodeRequestDto): OauthCodeResponseDto {
        val user = userRepository.findByEmail(oauthLoginRequestDto.email) ?: throw UserNotFoundException()
        when {
            user.state == UserState.OAUTH_BAN -> throw SignInBanException()
            tempOAuthSignInBanRepository.existsById(user.email) -> throw TempOAuthSignInBanException()
        }
        tooManyOAuthRequestValidUtil.validRequest(oauthLoginRequestDto.email)
        if (!passwordEncoder.matches(oauthLoginRequestDto.password, user.password)) {
            val updatedUser = userRepository.save(user.updateOAuthWrongPasswordCount(user.oauthWrongPasswordCount + 1))
            if (updatedUser.oauthWrongPasswordCount >= 5){
                tempOAuthSignInBanRepository.save(TempOAuthSignInBan(user.email))
                userRepository.save(user.updateOAuthWrongPasswordCount(0))
            }
            throw PasswordMismatchException()
        }
        if(user.state == UserState.PENDING)
            throw UserStatePendingException()
        val code = UUID.randomUUID().toString().split(".")[0]
        oauthCodeRepository.save(OauthCode(code, user.email))
        return OauthCodeResponseDto(
            code = code,
        )
    }

    fun execute(): OauthCodeResponseDto{
        val user = userUtil.fetchCurrentUser()
        if(user.state == UserState.PENDING)
            throw UserStatePendingException()
        val code = UUID.randomUUID().toString().split(".")[0]
        oauthCodeRepository.save(OauthCode(code, user.email))
        return OauthCodeResponseDto(
            code = code,
        )
    }
}