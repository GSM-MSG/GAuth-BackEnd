package com.msg.gauth.domain.oauth.presentation

import com.msg.gauth.domain.oauth.presentation.dto.request.OauthLoginRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthLoginResponseDto
import com.msg.gauth.domain.oauth.services.OauthLoginService
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/oauth")
class OauthController(
    val oauthLoginService: OauthLoginService,
){
    @PostMapping("/login")
    fun oauthLogin(@Validated @RequestBody oauthLoginRequestDto : OauthLoginRequestDto): ResponseEntity<OauthLoginResponseDto> =
        ResponseEntity.ok(oauthLoginService.execute(oauthLoginRequestDto))
}