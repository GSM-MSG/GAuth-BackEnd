package com.msg.gauth.domain.oauth.presentation

import com.msg.gauth.domain.oauth.presentation.dto.request.OauthCodeRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.request.OauthTokenRequestDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthCodeResponseDto
import com.msg.gauth.domain.oauth.presentation.dto.response.ServiceNameResponseDto
import com.msg.gauth.domain.oauth.presentation.dto.response.OauthTokenResponseDto
import com.msg.gauth.domain.oauth.service.GetServiceNameService
import com.msg.gauth.domain.oauth.service.GenerateOauthCodeService
import com.msg.gauth.domain.oauth.service.RefreshOauthTokenService
import com.msg.gauth.domain.oauth.service.GenerateOauthTokenService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/oauth")
class OauthController(
    private val generateOauthCodeService: GenerateOauthCodeService,
    private val generateOauthTokenService: GenerateOauthTokenService,
    private val refreshOauthTokenService: RefreshOauthTokenService,
    private val getServiceNameService: GetServiceNameService
) {

    @PostMapping("/code")
    fun generateOauthCode(@Valid @RequestBody oauthCodeRequestDto : OauthCodeRequestDto): ResponseEntity<OauthCodeResponseDto> {
        val result = generateOauthCodeService.execute(oauthCodeRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/code/access")
    fun generateOauthCode(): ResponseEntity<OauthCodeResponseDto> {
        val result = generateOauthCodeService.execute()
        return ResponseEntity.ok(result)
    }

    @PostMapping("/token")
    fun generateOauthToken(@Valid @RequestBody oauthTokenRequestDto: OauthTokenRequestDto): ResponseEntity<OauthTokenResponseDto> {
        val result = generateOauthTokenService.execute(oauthTokenRequestDto)
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/token")
    fun refreshOauthToken(@RequestHeader refreshToken: String): ResponseEntity<OauthTokenResponseDto> {
        val result = refreshOauthTokenService.execute(refreshToken)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/{clientId}")
    fun getServiceName(@PathVariable clientId: String): ResponseEntity<ServiceNameResponseDto> {
        val result = getServiceNameService.execute(clientId)
        return ResponseEntity.ok(result)
    }
}