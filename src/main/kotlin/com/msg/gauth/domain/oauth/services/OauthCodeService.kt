package com.msg.gauth.domain.oauth.services

import com.msg.gauth.domain.auth.exception.PasswordMismatchException
import com.msg.gauth.domain.oauth.OauthCode
import com.msg.gauth.domain.oauth.exception.UserStatePendingException
import com.msg.gauth.domain.oauth.presentation.dto.request.OauthCodeRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthCodeResponseDto
import com.msg.gauth.domain.oauth.repository.OauthCodeRepository
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class OauthCodeService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val oauthCodeRepository: OauthCodeRepository,
){
    @Transactional(readOnly = true, rollbackFor = [Exception::class])
    fun execute(oauthLoginRequestDto: OauthCodeRequestDto): OauthCodeResponseDto {
        val user = userRepository.findByEmail(oauthLoginRequestDto.email) ?: throw UserNotFoundException()
        if (!passwordEncoder.matches(oauthLoginRequestDto.password, user.password))
            throw PasswordMismatchException()
        if(user.state != UserState.CREATED)
            throw UserStatePendingException()
        val code = UUID.randomUUID().toString().split(".")[0]
        oauthCodeRepository.save(OauthCode(code, user.email))
        return OauthCodeResponseDto(
            code = code,
        )
    }
}