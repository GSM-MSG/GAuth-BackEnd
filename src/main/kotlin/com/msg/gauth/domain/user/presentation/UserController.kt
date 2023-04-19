package com.msg.gauth.domain.user.presentation

import com.msg.gauth.domain.user.presentation.dto.response.GetMyRolesResDto
import com.msg.gauth.domain.user.presentation.dto.request.AcceptStudentReqDto
import com.msg.gauth.domain.user.presentation.dto.request.AcceptTeacherReqDto
import com.msg.gauth.domain.user.presentation.dto.request.AcceptUserReqDto
import com.msg.gauth.domain.user.presentation.dto.request.PasswordChangeReqDto
import com.msg.gauth.domain.user.presentation.dto.response.MyProfileResDto
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.presentation.dto.response.SinglePendingListResDto
import com.msg.gauth.domain.user.services.*
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
    private val changePasswordService: ChangePasswordService,
    private val uploadProfileService: UploadProfileService,
    private val getMyProfileService: GetMyProfileService,
    private val getAcceptedUsersService: GetAcceptedUsersService,
    private val acceptTeacherSignUpService: AcceptTeacherSignUpService,
    private val getPendingUsersService: GetPendingUsersService,
    private val acceptStudentSignUpService: AcceptStudentSignUpService,
    private val getMyRolesService: GetMyRolesService,
    private val acceptUserSignUpService: AcceptUserSignUpService,
    private val rejectSignUpUserService: RejectSignUpUserService
) {
    @GetMapping("/role")
    fun getMyRoles(): ResponseEntity<GetMyRolesResDto> {
        val result = getMyRolesService.execute()
        return ResponseEntity.ok(result)
    }

    @GetMapping
    fun myProfile(): ResponseEntity<MyProfileResDto> {
        val result = getMyProfileService.execute()
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
    fun acceptedUserList(@RequestParam(defaultValue = "0") grade: Int,
                         @RequestParam(defaultValue = "0") classNum: Int,
                         @RequestParam(defaultValue = "") keyword: String
    ): ResponseEntity<List<SingleAcceptedUserResDto>> {
        val result = getAcceptedUsersService.execute(grade, classNum, keyword)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/pending")
    fun pendingList(): ResponseEntity<List<SinglePendingListResDto>> {
        val result = getPendingUsersService.execute()
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/accept-user/{id}")
    fun acceptUser(@PathVariable id: Long, @RequestBody @Valid acceptUserReqDto: AcceptUserReqDto): ResponseEntity<Void>{
        acceptUserSignUpService.execute(id, acceptUserReqDto)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/reject/{id}")
    fun rejectUser(@PathVariable id: Long): ResponseEntity<Void> {
        rejectSignUpUserService.execute(id)
        return ResponseEntity.noContent().build()
    }

    @Deprecated("This api is deprecated. Use acceptUser instead")
    @PatchMapping("/accept-teacher")
    fun acceptTeacher(@RequestBody @Valid acceptTeacherReqDto: AcceptTeacherReqDto): ResponseEntity<Void>{
        acceptTeacherSignUpService.execute(acceptTeacherReqDto)
        return ResponseEntity.noContent().build()
    }

    @Deprecated("This api is deprecated. Use acceptUser instead")
    @PatchMapping("/accept-student")
    fun acceptStudent(@RequestBody @Valid acceptedStudentReqDto: AcceptStudentReqDto): ResponseEntity<Void> {
        acceptStudentSignUpService.execute(acceptedStudentReqDto)
        return ResponseEntity.noContent().build()
    }
}