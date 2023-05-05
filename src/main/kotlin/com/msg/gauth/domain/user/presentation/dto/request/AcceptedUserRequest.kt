package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.UserRole
import org.springframework.web.bind.annotation.RequestParam

data class AcceptedUserRequest(
    @RequestParam(defaultValue = "0") val grade: Int,
    @RequestParam(defaultValue = "0") val classNum: Int,
    @RequestParam(defaultValue = "") val keyword: String,
    @RequestParam(defaultValue = "ROLE_STUDENT") val role: UserRole
)