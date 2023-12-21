package com.msg.gauth.domain.user.enums

import org.springframework.security.core.GrantedAuthority

enum class UserRoleType : GrantedAuthority {
    ROLE_STUDENT, ROLE_TEACHER, ROLE_ADMIN, ROLE_GRADUATE;

    override fun getAuthority(): String =
        name
}
