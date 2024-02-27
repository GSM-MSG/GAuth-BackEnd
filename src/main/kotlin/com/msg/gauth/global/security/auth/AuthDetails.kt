package com.msg.gauth.global.security.auth

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserState
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class AuthDetails(
    private val user: User
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?> =
        user.userRoles.map { it.userRoleType }

    override fun getPassword(): String? =
        null

    override fun getUsername(): String =
        user.email

    override fun isAccountNonExpired(): Boolean =
        true

    override fun isAccountNonLocked(): Boolean =
        user.state.equals(UserState.CREATED)


    override fun isCredentialsNonExpired(): Boolean =
        true

    override fun isEnabled(): Boolean =
        isAccountNonExpired && isAccountNonLocked && isCredentialsNonExpired
}