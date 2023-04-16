package com.msg.gauth.domain.user.repository

interface CustomUserRepository {
    fun searchUser(grade: Int, classNum: Int, keyword: String)
}