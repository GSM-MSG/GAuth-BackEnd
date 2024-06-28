package com.msg.gauth.domain.user.presentation

import com.msg.gauth.domain.user.presentation.dto.request.*
import com.msg.gauth.domain.user.presentation.dto.response.GetMyRolesResDto
import com.msg.gauth.domain.user.presentation.dto.response.MyProfileResDto
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.presentation.dto.response.SinglePendingListResDto
import com.msg.gauth.domain.user.service.*
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(
    private val changePasswordService: ChangePasswordService,
    private val getMyProfileService: GetMyProfileService,
    private val getAcceptedUsersService: GetAcceptedUsersService,
    private val getPendingUsersService: GetPendingUsersService,
    private val getMyRolesService: GetMyRolesService,
    private val acceptUserSignUpService: AcceptUserSignUpService,
    private val rejectUserSignUpService: RejectUserSignUpService,
    private val uploadProfileURLService: UploadProfileURLService
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
    fun changePassword(@Valid @RequestBody passwordChangeReqDto: PasswordChangeReqDto): ResponseEntity<Void> {
        changePasswordService.execute(passwordChangeReqDto)
        return ResponseEntity.noContent().build()
    }

    @PatchMapping("/profile")
    fun uploadProfileURL(@RequestParam("image_url") imageURL: String): ResponseEntity<Void> {
        uploadProfileURLService.execute(imageURL)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/user-list")
    fun acceptedUserList(request: AcceptedUserRequest): ResponseEntity<List<SingleAcceptedUserResDto>> {
        val result = getAcceptedUsersService.execute(request)
        return ResponseEntity.ok(result)
    }

    @GetMapping("/pending")
    fun pendingList(): ResponseEntity<List<SinglePendingListResDto>> {
        val result = getPendingUsersService.execute()
        return ResponseEntity.ok(result)
    }

    @PatchMapping("/accept-user/{id}")
    fun acceptUser(@PathVariable id: Long, @Valid @RequestBody acceptUserReqDto: AcceptUserReqDto): ResponseEntity<Void> {
        acceptUserSignUpService.execute(id, acceptUserReqDto)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/reject/{id}")
    fun rejectUser(@PathVariable id: Long): ResponseEntity<Void> {
        rejectUserSignUpService.execute(id)
        return ResponseEntity.noContent().build()
    }
}