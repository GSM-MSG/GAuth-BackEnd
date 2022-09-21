package com.msg.gauth.global.util

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.exception.UserNotFoundException
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.global.security.auth.AuthDetails
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class CurrentUserUtil(
    private val userRepository: UserRepository
) {
    private fun getCurrentEmail(): String{
        val principal = SecurityContextHolder.getContext().authentication.principal
        val email: String =
        if(principal is UserDetails){
            (principal as AuthDetails).username
        }else{
            principal.toString()
        }
        return email
    }

    fun getCurrentUser(): User =
        userRepository.findByEmail(getCurrentEmail())?: throw UserNotFoundException()
}