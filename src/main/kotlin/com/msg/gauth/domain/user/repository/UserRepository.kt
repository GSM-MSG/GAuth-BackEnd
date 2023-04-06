package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByEmail(email: String): User?
    fun findByEmailIn(emailList: List<String>): List<User>
    fun existsByEmail(email: String): Boolean
    @Query("select user.email from User user")
    fun findAllEmail(): List<String>
    fun findAllByState(state: UserState): List<User>
    fun findByIdAndState(id: Long, roles: UserState): User?
    fun findAllByStateOrderByGrade(state: UserState, pageable: Pageable): List<User>
    fun findAllByStateAndNameContainingOrderByGrade(state: UserState, name: String, pageable: Pageable): List<User>
    fun findAllByStateAndClassNumOrderByGrade(state: UserState, classNum: Int, pageable: Pageable): List<User>
    fun findAllByStateAndGradeOrderByGrade(state: UserState, grade: Int, pageable: Pageable): List<User>
    fun findAllByStateAndClassNumAndNameContainingOrderByGrade(state: UserState, classNum: Int, name: String, pageable: Pageable): List<User>
    fun findAllByStateAndGradeAndNameContainingOrderByGrade(state: UserState, grade: Int, name: String, pageable: Pageable): List<User>
    fun findAllByStateAndGradeAndClassNumOrderByGrade(state: UserState, grade: Int, classNum: Int, pageable: Pageable): List<User>
    fun findAllByStateAndGradeAndClassNumAndNameContainingOrderByGrade(state: UserState, grade: Int, classNum: Int, name: String, pageable: Pageable): List<User>
    @Query("select user from User user where user.id = :id and user.state = :state and user.roles = :roles")
    fun findByIdAndStateAndRoles(id: Long, state: UserState, roles: MutableList<UserRole>): User?
}