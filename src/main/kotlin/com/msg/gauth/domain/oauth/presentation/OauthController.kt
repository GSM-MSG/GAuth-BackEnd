package com.msg.gauth.domain.oauth.presentation

import com.msg.gauth.domain.oauth.presentation.dto.request.OauthCodeRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.request.OauthLoginReqDto
import com.msg.gauth.domain.oauth.presentation.dto.request.UserTokenRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthCodeResponseDto
import com.msg.gauth.domain.oauth.presentation.dto.response.ServiceNameResponseDto
import com.msg.gauth.domain.oauth.presentation.dto.response.UserTokenResponseDto
import com.msg.gauth.domain.oauth.services.GetServiceNameService
import com.msg.gauth.domain.oauth.services.OauthCodeService
import com.msg.gauth.domain.oauth.services.OauthRefreshService
import com.msg.gauth.domain.oauth.services.OauthTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/oauth")
class OauthController(
    private val oauthCodeService: OauthCodeService,
    private val oauthTokenService: OauthTokenService,
    private val oauthRefreshService: OauthRefreshService,
    private val getServiceNameService: GetServiceNameService,
){
    @PostMapping("/code")
    @CrossOrigin("*")
    fun generateOauthCode(@Valid @RequestBody oauthCodeRequestDto : OauthCodeRequestDto): ResponseEntity<OauthCodeResponseDto> =
        ResponseEntity.ok(oauthCodeService.execute(oauthCodeRequestDto))

    @PostMapping("/token")
    @CrossOrigin("*")
    fun generateOauthToken(@RequestBody userTokenRequestDto: UserTokenRequestDto): ResponseEntity<UserTokenResponseDto> =
        ResponseEntity.ok(oauthTokenService.execute(userTokenRequestDto))

    @PatchMapping("/token")
    @CrossOrigin("*")
    fun refreshOauthToken(@RequestHeader refreshToken: String): ResponseEntity<UserTokenResponseDto> =
        ResponseEntity.ok(oauthRefreshService.execute(refreshToken))

    @GetMapping("/{clientId}")
    fun getServiceName(@PathVariable clientId: String): ResponseEntity<ServiceNameResponseDto> =
        ResponseEntity.ok(getServiceNameService.execute(clientId))
}