package com.msg.gauth.domain.user.presentation

import com.msg.gauth.domain.user.presentation.dto.request.PasswordChangeReqDto
import com.msg.gauth.domain.user.presentation.dto.response.MyProfileResDto
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.services.AcceptedUserService
import com.msg.gauth.domain.user.services.ChangePasswordService
import com.msg.gauth.domain.user.services.MyProfileService
import com.msg.gauth.domain.user.services.UploadProfileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
    private val changePasswordService: ChangePasswordService,
    private val uploadProfileService: UploadProfileService,
    private val myProfileService: MyProfileService,
    private val acceptedUserService: AcceptedUserService,
) {
    @GetMapping
    fun myProfile(): ResponseEntity<MyProfileResDto> {
        val result = myProfileService.execute()
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/password")
    fun changePassword(@Valid @RequestBody passwordChangeReqDto: PasswordChangeReqDto): ResponseEntity<Void>{
        changePasswordService.execute(passwordChangeReqDto)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/image")
    fun uploadProfile(@RequestPart("image") image: MultipartFile): ResponseEntity<Void>{
        uploadProfileService.execute(image)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/user-list")
    fun acceptedUserList(@RequestParam grade: Int, @RequestParam classNum: Int, @RequestParam keyword: String): ResponseEntity<List<SingleAcceptedUserResDto>> {
        val result = acceptedUserService.execute(grade, classNum, keyword)
        return ResponseEntity.ok(result)
    }
}