package com.msg.gauth.domain.oauth.presentation

import com.msg.gauth.domain.oauth.presentation.dto.request.OauthCodeRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.request.UserTokenRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthCodeResponseDto
import com.msg.gauth.domain.oauth.presentation.dto.response.UserTokenResponseDto
import com.msg.gauth.domain.oauth.services.OauthLoginService
import com.msg.gauth.domain.oauth.services.OauthTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/oauth")
class OauthController(
    val oauthLoginService: OauthLoginService,
    val oauthTokenService: OauthTokenService,
){
    @PostMapping("/code")
    fun oauthLogin(@Valid @RequestBody oauthCodeRequestDto : OauthCodeRequestDto): ResponseEntity<OauthCodeResponseDto> =
        ResponseEntity.ok(oauthLoginService.execute(oauthCodeRequestDto))

    @PostMapping("/token")
    fun execute(@RequestBody userTokenRequestDto: UserTokenRequestDto): ResponseEntity<UserTokenResponseDto> =
        ResponseEntity.ok(oauthTokenService.execute(userTokenRequestDto))
}