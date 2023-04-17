package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User

interface CustomUserRepository {
    fun searchUser(grade: Int, classNum: Int, keyword: String): List<User>
}