package com.msg.gauth.domain.user.presentation

import com.msg.gauth.domain.user.presentation.dto.request.PasswordChangeReqDto
import com.msg.gauth.domain.user.services.ChangePasswordService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
    private val changePasswordService: ChangePasswordService,
){
    @PatchMapping("/password")
    fun changePassword(@Valid @RequestBody passwordChangeReqDto: PasswordChangeReqDto): ResponseEntity<Void>{
        changePasswordService.execute(passwordChangeReqDto)
        return ResponseEntity.ok().build()
    }
}