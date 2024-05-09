package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRoleType

interface CustomUserRepository {
    fun search(grade: Int, classNum: Int, keyword: String): List<User>
    fun findAllByUserRoleType(userRoleType: UserRoleType): List<User>
}