package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query

interface UserRepository: JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    @Query("select user.email from User user")
    fun findAllEmail(): List<String>



    fun findAllByState(state: UserState): List<User>
    fun findAllByStateAndNameContaining(state: UserState, name: String): List<User>
    fun findAllByStateAndClassNum(state: UserState, classNum: Int): List<User>
    fun findAllByStateAndGrade(state: UserState, grade: Int): List<User>
    fun findAllByStateAndClassNumAndNameContaining(state: UserState, classNum: Int, name: String): List<User>
    fun findAllByStateAndGradeAndNameContaining(state: UserState, grade: Int, name: String): List<User>
    fun findAllByStateAndGradeAndClassNum(state: UserState, grade: Int, classNum: Int): List<User>
    fun findAllByStateAndGradeAndClassNumAndNameContaining(state: UserState, grade: Int, classNum: Int, name: String): List<User>
    @Query("select user from User user where user.id = :id and user.state = :state and user.roles = :roles")
    fun findByIdAndStateAndRoles(id: Long, state: UserState, roles: MutableList<UserRole>): User?

    fun findByStateAndGradeAndClassNumAndNameContaining(state: UserState, grade: Int, classNum: Int, name: String): List<User> {
        return when {
            grade == 0 && classNum == 0 && name == "0" -> findAllByState(state)
            grade == 0 && classNum == 0 -> findAllByStateAndNameContaining(state, name)
            classNum == 0 && name == "0" -> findAllByStateAndClassNum(state, classNum)
            grade == 0 && name == "0"-> findAllByStateAndGrade(state, grade)
            grade == 0 -> findAllByStateAndClassNumAndNameContaining(state, classNum, name)
            classNum == 0 -> findAllByStateAndGradeAndNameContaining(state, grade, name)
            name == "0" -> findAllByStateAndGradeAndClassNum(state, grade, classNum)
            else -> findAllByStateAndGradeAndClassNumAndNameContaining(state, grade, classNum, name)
        }
    }
}