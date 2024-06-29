package com.msg.gauth.domain.oauth.service

import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.oauth.OauthCode
import com.msg.gauth.domain.oauth.exception.UserStatePendingException
import com.msg.gauth.domain.oauth.presentation.dto.request.OauthCodeRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthCodeResponseDto
import com.msg.gauth.domain.oauth.repository.OauthCodeRepository
import com.msg.gauth.global.util.count.oauth.util.TooManyOAuthRequestValidUtil
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.util.TempUserUtil
import com.msg.gauth.domain.user.util.UserUtil
import com.msg.gauth.global.annotation.service.TransactionalService
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@TransactionalService(noRollbackFor = [PasswordMismatchException::class])
class GenerateOauthCodeService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val oauthCodeRepository: OauthCodeRepository,
    private val tooManyOAuthRequestValidUtil: TooManyOAuthRequestValidUtil,
    private val tempUserUtil: TempUserUtil,
    private val userUtil: UserUtil
) {

    fun execute(oauthLoginRequestDto: OauthCodeRequestDto): OauthCodeResponseDto {
        val user = userRepository.findByEmail(oauthLoginRequestDto.email)
            ?: throw UserNotFoundException()

        tempUserUtil.isUserOAuthBan(user)

        tooManyOAuthRequestValidUtil.validRequest(oauthLoginRequestDto.email)

        if (!passwordEncoder.matches(oauthLoginRequestDto.password, user.password)) {
            tempUserUtil.validOAuthWrongCount(user)
            throw PasswordMismatchException()
        }

        if(user.state == UserState.PENDING)
            throw UserStatePendingException()

        tempUserUtil.resetOAuthWrongPasswordCount(user)

        val code = UUID.randomUUID().toString().split(".")[0]

        oauthCodeRepository.save(OauthCode(code, user.email))
        return OauthCodeResponseDto(
            code = code,
        )
    }

    fun execute(): OauthCodeResponseDto {
        val user = userUtil.fetchCurrentUser()

        tempUserUtil.isUserBan(user)
        tooManyOAuthRequestValidUtil.validRequest(user.email)

        if(user.state == UserState.PENDING)
            throw UserStatePendingException()

        val code = UUID.randomUUID().toString().split(".")[0]

        oauthCodeRepository.save(OauthCode(code, user.email))

        return OauthCodeResponseDto(
            code = code
        )
    }
}