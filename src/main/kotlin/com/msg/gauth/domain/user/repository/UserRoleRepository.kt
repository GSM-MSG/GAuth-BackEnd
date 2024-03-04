package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.UserRole
import org.springframework.data.repository.CrudRepository

interface UserRoleRepository:  CrudRepository<UserRole, Long> {
    fun findByUser(user: User): List<UserRole>
}