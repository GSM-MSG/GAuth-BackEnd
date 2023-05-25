package com.msg.gauth.domain.user.util

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.security.auth.AuthDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class UserUtil(
    private val userRepository: UserRepository
) {
    fun fetchCurrentUser(): User {
        val principal = SecurityContextHolder.getContext().authentication.principal
        val email = if (principal is UserDetails) {
            (principal as AuthDetails).username
        } else {
            principal.toString()
        }
        return fetchUserByEmail(email)
    }

    fun fetchUserByEmail(email: String): User =
        userRepository.findByEmail(email) ?: throw UserNotFoundException()
}