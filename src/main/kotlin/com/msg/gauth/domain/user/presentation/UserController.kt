package com.msg.gauth.domain.user.presentation

import GetMyRolesResponseDto
import GetMyrolesResponseDto
import com.msg.gauth.domain.user.presentation.dto.request.AcceptStudentReqDto
import com.msg.gauth.domain.user.presentation.dto.request.AcceptTeacherReqDto
import com.msg.gauth.domain.user.presentation.dto.request.PasswordChangeReqDto
import com.msg.gauth.domain.user.presentation.dto.response.MyProfileResDto
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.presentation.dto.response.SinglePendingListResDto
import com.msg.gauth.domain.user.services.*
import org.springframework.data.domain.Pageable
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
    private val acceptTeacherSignUpService: AcceptTeacherSignUpService,
    private val pendingListService: PendingListService,
    private val acceptStudentSignUpService: AcceptStudentSignUpService
) {
    @GetMapping("/role")
    fun getMyRoles(): ResponseEntity<GetMyRolesResponseDto> {
        return ResponseEntity.ok().build()
    }

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
    fun acceptedUserList(@RequestParam grade: Int,
                         @RequestParam classNum: Int,
                         @RequestParam keyword: String,
                         pageable: Pageable
    ): ResponseEntity<List<SingleAcceptedUserResDto>> {
        val result = acceptedUserService.execute(grade, classNum, keyword, pageable)
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/accept-teacher")
    fun acceptTeacher(@RequestBody @Valid acceptTeacherReqDto: AcceptTeacherReqDto): ResponseEntity<Void>{
        acceptTeacherSignUpService.execute(acceptTeacherReqDto)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/pending")
    fun pendingList(): ResponseEntity<List<SinglePendingListResDto>> {
        val result = pendingListService.execute()
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/accept-student")
    fun acceptStudent(@RequestBody @Valid acceptedStudentReqDto: AcceptStudentReqDto): ResponseEntity<Void> {
        acceptStudentSignUpService.execute(acceptedStudentReqDto)
        return ResponseEntity.noContent().build()
    }
}