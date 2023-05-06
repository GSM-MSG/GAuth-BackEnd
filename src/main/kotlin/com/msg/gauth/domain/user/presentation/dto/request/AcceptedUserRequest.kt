package com.msg.gauth.domain.user.presentation.dto.request

import com.msg.gauth.domain.user.enums.UserRole
import org.springframework.web.bind.annotation.RequestParam

data class AcceptedUserRequest(
    @RequestParam val grade: Int = 0,
    @RequestParam val classNum: Int = 0,
    @RequestParam val keyword: String = "",
    @RequestParam val role: UserRole = UserRole.ROLE_STUDENT
)