package com.msg.gauth.domain.oauth.presentation

import com.msg.gauth.domain.oauth.presentation.dto.request.OauthLoginRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.request.UserInfoRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthLoginResponseDto
import com.msg.gauth.domain.oauth.presentation.dto.response.UserInfoResponseDto
import com.msg.gauth.domain.oauth.services.OauthLoginService
import com.msg.gauth.domain.oauth.services.OauthUserInfoService
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
    val oauthUserInfoService: OauthUserInfoService,
){
    @PostMapping("/login")
    fun oauthLogin(@Validated @RequestBody oauthLoginRequestDto : OauthLoginRequestDto): ResponseEntity<OauthLoginResponseDto> =
        ResponseEntity.ok(oauthLoginService.execute(oauthLoginRequestDto))

    @PostMapping("/user")
    fun getUserInfo(@RequestBody userInfoRequestDto: UserInfoRequestDto): ResponseEntity<UserInfoResponseDto> =
        ResponseEntity.ok(oauthUserInfoService.execute(userInfoRequestDto))
}