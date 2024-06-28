package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, CustomUserRepository {
    @EntityGraph(attributePaths = ["userRole"])
    fun findByEmail(email: String): User?
    fun findByEmailIn(emailList: List<String>): List<User>
    fun existsByEmail(email: String): Boolean
    fun findAllByState(state: UserState): List<User>
    fun findByIdAndState(id: Long, roles: UserState): User?
    fun existsByGradeAndClassNumAndNum(grade: Int, classNum: Int, num: Int): Boolean
}