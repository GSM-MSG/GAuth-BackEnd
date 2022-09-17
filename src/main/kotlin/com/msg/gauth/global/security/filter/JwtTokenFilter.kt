package com.msg.gauth.global.security.filter

import com.msg.gauth.global.security.jwt.JwtTokenProvider
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtTokenFilter(
    private val jwtTokenProvider: JwtTokenProvider
): OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = jwtTokenProvider.resolveToken(request)
        token?.let {
            print(it)
            val authentication = jwtTokenProvider.authentication(token)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }
}