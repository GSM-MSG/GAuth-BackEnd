package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    @Query("select user.email from User user")
    fun findAllEmail(): List<String>
}