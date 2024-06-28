package com.msg.gauth.domain.auth.presentation

import com.msg.gauth.domain.auth.presentation.dto.request.*
import com.msg.gauth.domain.auth.presentation.dto.response.RefreshResponseDto
import com.msg.gauth.domain.auth.service.*
import com.msg.gauth.domain.auth.presentation.dto.response.SignInResponseDto
import com.msg.gauth.domain.auth.presentation.dto.response.SignUpImageResDto
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
    private val updatePasswordService: UpdatePasswordService,
    private val signUpEmailVerificationService: SignUpEmailVerificationService,
    private val signUpMemberV2Service: SignUpMemberV2Service
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
    fun signIn(@RequestBody @Valid signInRequestDto: SignInRequestDto): ResponseEntity<SignInResponseDto> {
        val result = signInService.execute(signInRequestDto)
        return ResponseEntity.ok(result)
    }

    @PostMapping("/signup")
    fun signUpMember(@RequestBody @Valid signUpRequestDto: SignUpRequestDto): ResponseEntity<Void> {
        signUpService.execute(signUpRequestDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PostMapping("/v2/signup")
    fun signUpMemberV2(@RequestBody @Valid signUpV2RequestDto: SignUpV2RequestDto): ResponseEntity<Void> {
        signUpMemberV2Service.execute(signUpV2RequestDto)
        return ResponseEntity(HttpStatus.CREATED)
    }

    @PatchMapping("/image")
    fun uploadSignupImage(
        @RequestPart("image") image: MultipartFile,
        @RequestPart("imageUrl") previousUrl: String?,
        @RequestPart email: String
    ): ResponseEntity<SignUpImageResDto> {
        val result = signUpImageUploadService.execute(image, previousUrl, email)
        return ResponseEntity.ok(result)
    }


    @PatchMapping("/password/initialize")
    fun initPassword(@RequestBody @Valid initPasswordRequestDto: InitPasswordRequestDto): ResponseEntity<Void> {
        initPasswordService.execute(initPasswordRequestDto)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/password")
    fun updatePassword(@RequestBody @Valid updatePasswordRequestDto: UpdatePasswordRequestDto): ResponseEntity<Void> {
        updatePasswordService.execute(updatePasswordRequestDto)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    fun signUpEmailVerification(@RequestParam email: String): ResponseEntity<Void> {
        signUpEmailVerificationService.execute(email)
        return ResponseEntity.ok().build()
    }

}