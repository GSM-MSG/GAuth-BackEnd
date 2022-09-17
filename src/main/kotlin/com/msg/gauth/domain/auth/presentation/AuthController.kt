package com.msg.gauth.domain.auth.presentation

import com.msg.gauth.domain.auth.presentation.dto.request.SignUpDto
import com.msg.gauth.domain.auth.presentation.dto.request.SigninRequestDto
import com.msg.gauth.domain.auth.presentation.dto.response.RefreshResponseDto
import com.msg.gauth.domain.auth.presentation.dto.response.SigninResponseDto
import com.msg.gauth.domain.auth.services.LogoutService
import com.msg.gauth.domain.auth.services.RefreshService
import com.msg.gauth.domain.auth.services.SignUpService
import com.msg.gauth.domain.auth.services.SignInService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    private val refreshService: RefreshService,
    private val logoutService: LogoutService,
    private val signInService: SignInService,
    private val signUpService: SignUpService
) {

    @PatchMapping
    fun refresh(@RequestHeader refreshToken: String): ResponseEntity<RefreshResponseDto> =
        ResponseEntity.ok(refreshService.execute(refreshToken))

    @DeleteMapping
    fun logout(): ResponseEntity<Void> {
        logoutService.execute()
        return ResponseEntity.noContent().build()
    }

    @PostMapping
    fun signin(@Valid @RequestBody signinRequestDto: SigninRequestDto): ResponseEntity<SigninResponseDto> =
        ResponseEntity.ok(signInService.execute(signinRequestDto))

    @PostMapping("/signup")
    fun signUpMember(@Valid @RequestBody signUpDto: SignUpDto): ResponseEntity<Void> {
        signUpService.execute(signUpDto)
        return ResponseEntity(HttpStatus.CREATED)
    }
}