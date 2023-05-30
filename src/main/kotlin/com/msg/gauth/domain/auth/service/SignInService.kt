package com.msg.gauth.domain.auth.service

import com.msg.gauth.domain.auth.RefreshToken
import com.msg.gauth.domain.auth.TempSignInBan
import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.auth.exception.SignInBanException
import com.msg.gauth.domain.auth.exception.TempSignInBanException
import com.msg.gauth.domain.auth.exception.UserIsPendingException
import com.msg.gauth.domain.auth.presentation.dto.request.SignInRequestDto
import com.msg.gauth.domain.auth.presentation.dto.response.SigninResponseDto
import com.msg.gauth.domain.auth.repository.RefreshTokenRepository
import com.msg.gauth.domain.auth.repository.TempSignInBanRepository
import com.msg.gauth.domain.auth.util.TooManyRequestValidUtil
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.annotation.service.TransactionalService
import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.crypto.password.PasswordEncoder

@TransactionalService(noRollbackFor = [PasswordMismatchException::class])
class SignInService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tooManyRequestValidUtil: TooManyRequestValidUtil,
    private val tempSignInBanRepository: TempSignInBanRepository
) {
    fun execute(dto: SignInRequestDto): SigninResponseDto {
        val user: User = userRepository.findByEmail(dto.email) ?: throw UserNotFoundException()

        isUserBan(user)

        tooManyRequestValidUtil.validRequest(dto.email)

        if (!passwordEncoder.matches(dto.password, user.password)) {
            validWrongCount(user)
            throw PasswordMismatchException()
        }

        if(user.state != UserState.CREATED)
            throw UserIsPendingException()

        val (access, refresh) = jwtTokenProvider.run {
            generateAccessToken(dto.email) to generateRefreshToken(dto.email)}

        val expiresAt = jwtTokenProvider.accessExpiredTime

        refreshTokenRepository.save(RefreshToken(user.id, refresh))
        return SigninResponseDto(access, refresh, expiresAt)
    }

    private fun isUserBan(user: User) {
        when {
            user.state == UserState.SIGN_IN_BAN -> throw SignInBanException()
            tempSignInBanRepository.existsById(user.email) -> throw TempSignInBanException()
        }
    }

    private fun validWrongCount(user: User) {
        val updatedUser = userRepository.save(user.updateWrongPasswordCount(user.wrongPasswordCount + 1))
        if (updatedUser.wrongPasswordCount >= 5) {
            tempSignInBanRepository.save(TempSignInBan(user.email))
            userRepository.save(user.updateWrongPasswordCount(0))
        }
    }
}