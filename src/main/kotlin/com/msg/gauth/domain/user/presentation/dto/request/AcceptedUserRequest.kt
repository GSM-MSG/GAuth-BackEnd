package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.UserRole
import org.springframework.web.bind.annotation.RequestParam

data class AcceptedUserRequest(
    @RequestParam(required = false) val grade: Int = 0,
    @RequestParam(required = false) val classNum: Int = 0,
    @RequestParam(required = false) val keyword: String = "",
    @RequestParam(required = false) val role: UserRole = UserRole.ROLE_STUDENT
)