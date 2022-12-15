package com.msg.gauth.domain.user.presentation

import com.msg.gauth.domain.user.presentation.dto.request.PasswordChangeReqDto
import com.msg.gauth.domain.user.services.ChangePasswordService
import com.msg.gauth.domain.user.services.UploadProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
    private val changePasswordService: ChangePasswordService,
    private val uploadProfileService: UploadProfileService,
){
    @PatchMapping("/password")
    fun changePassword(@Valid @RequestBody passwordChangeReqDto: PasswordChangeReqDto): ResponseEntity<Void>{
        changePasswordService.execute(passwordChangeReqDto)
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/image")
    fun uploadProfile(@RequestParam("image") image: MultipartFile): ResponseEntity<Void>{
        uploadProfileService.execute(image)
        return ResponseEntity.noContent().build()
    }
}