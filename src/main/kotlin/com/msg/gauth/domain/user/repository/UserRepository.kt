package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    @Query("select user.email from User user")
    fun findAllEmail(): List<String>
    @Query("select user from User user where user.id = :id and user.state = :state and user.roles = :roles")
    fun findByIdAndStateAndRoles(id: Long, state: UserState, roles: MutableList<UserRole>): User?
}