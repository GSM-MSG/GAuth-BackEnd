package com.msg.gauth.domain.auth.presentation

import com.msg.gauth.domain.auth.presentation.dto.request.SignUpDto
import com.msg.gauth.domain.auth.presentation.dto.request.SignInRequestDto
import com.msg.gauth.domain.auth.presentation.dto.response.RefreshResponseDto
import com.msg.gauth.domain.auth.service.*
import com.msg.gauth.domain.auth.presentation.dto.request.PasswordInitReqDto
import com.msg.gauth.domain.auth.presentation.dto.response.SigninResponseDto
import com.msg.gauth.domain.auth.presentation.dto.response.SignupImageResDto
import com.msg.gauth.domain.auth.service.InitPasswordService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(
    private val refreshService: RefreshService,
    private val logoutService: LogoutService,
    private val signInService: SignInService,
    private val signUpService: SignUpService,
    private val initPasswordService: InitPasswordService,
    private val signUpImageUploadService: SignUpImageUploadService,
) {
    @PatchMapping
    fun refresh(@RequestHeader("RefreshToken") refreshToken: String): ResponseEntity<RefreshResponseDto> {
        val result = refreshService.execute(refreshToken)
        return ResponseEntity.ok(result)
    }

    @DeleteMapping
    fun logout(): ResponseEntity<Void> {
        logoutService.execute()
        return ResponseEntity.noContent().build()
    }

    @PostMapping
    fun signin(@Valid @RequestBody signInRequestDto: SignInRequestDto): ResponseEntity<SigninResponseDto> {
        val result = signInService.execute(signInRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/signup")
    fun signUpMember(@Valid @RequestBody signUpDto: SignUpDto): ResponseEntity<Void> {
        signUpService.execute(signUpDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PatchMapping("/image")
    fun uploadSignupImage(@RequestPart("image") image: MultipartFile, @RequestPart("imageUrl") previousUrl: String?, @RequestPart email: String): ResponseEntity<SignupImageResDto> {
        val result = signUpImageUploadService.execute(image, previousUrl, email)
        return ResponseEntity.ok(result)
    }


    @PatchMapping("/password/initialize")
    fun initPassword(@Valid @RequestBody passwordInitReqDto: PasswordInitReqDto): ResponseEntity<Void> {
        initPasswordService.execute(passwordInitReqDto)
        return ResponseEntity.noContent().build()
    }
}