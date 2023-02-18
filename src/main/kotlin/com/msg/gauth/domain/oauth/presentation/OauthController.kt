package com.msg.gauth.domain.oauth.presentation

import com.msg.gauth.domain.oauth.presentation.dto.request.OauthCodeRequestDto
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
) {
    @PostMapping("/code")
    fun generateOauthCode(@Valid @RequestBody oauthCodeRequestDto : OauthCodeRequestDto): ResponseEntity<OauthCodeResponseDto> {
        val result = oauthCodeService.execute(oauthCodeRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/token")
    fun generateOauthToken(@Valid @RequestBody userTokenRequestDto: UserTokenRequestDto): ResponseEntity<UserTokenResponseDto> {
        val result = oauthTokenService.execute(userTokenRequestDto)
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/token")
    fun refreshOauthToken(@RequestHeader refreshToken: String): ResponseEntity<UserTokenResponseDto> {
        val result = oauthRefreshService.execute(refreshToken)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{clientId}")
    fun getServiceName(@PathVariable clientId: String): ResponseEntity<ServiceNameResponseDto> {
        val result = getServiceNameService.execute(clientId)
        return ResponseEntity.ok(result)
    }
}