package com.msg.gauth.domain.oauth.presentation

import com.msg.gauth.domain.oauth.presentation.dto.request.OauthCodeRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.request.UserTokenRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthCodeResponseDto
import com.msg.gauth.domain.oauth.presentation.dto.response.UserTokenResponseDto
import com.msg.gauth.domain.oauth.services.OauthCodeService
import com.msg.gauth.domain.oauth.services.OauthRefreshService
import com.msg.gauth.domain.oauth.services.OauthTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/oauth")
class OauthController(
    val oauthCodeService: OauthCodeService,
    val oauthTokenService: OauthTokenService,
    val oauthRefreshService: OauthRefreshService,
){
    @PostMapping("/code")
    fun generateOauthCode(@Valid @RequestBody oauthCodeRequestDto : OauthCodeRequestDto): ResponseEntity<OauthCodeResponseDto> =
        ResponseEntity.ok(oauthCodeService.execute(oauthCodeRequestDto))

    @PostMapping("/token")
    fun generateOauthToken(@RequestBody userTokenRequestDto: UserTokenRequestDto): ResponseEntity<UserTokenResponseDto> =
        ResponseEntity.ok(oauthTokenService.execute(userTokenRequestDto))

    @PostMapping("/refresh")
    fun refreshOauthToken(@RequestHeader refreshToken: String): ResponseEntity<UserTokenResponseDto> =
        ResponseEntity.ok(oauthRefreshService.execute(refreshToken))
}