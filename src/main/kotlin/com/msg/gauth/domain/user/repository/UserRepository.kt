package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRoleType
import com.msg.gauth.domain.user.enums.UserState
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long>, CustomUserRepository {
    fun findByEmail(email: String): User?
    fun findByEmailIn(emailList: List<String>): List<User>
    fun existsByEmail(email: String): Boolean
    @Query("select user.email from User user")
    fun findAllEmail(): List<String>
    fun findAllByState(state: UserState): List<User>
    fun findAllByState(state: UserState, pageable: Pageable): Page<User>
    fun findByIdAndState(id: Long, roles: UserState): User?
    fun findAllByRolesContaining(role: UserRoleType): List<User>

    @Query("select user from User user where user.id = :id and user.state = :state and user.roles = :roles")
    fun findByIdAndStateAndRoles(id: Long, state: UserState, roles: MutableList<UserRoleType>): User?
}