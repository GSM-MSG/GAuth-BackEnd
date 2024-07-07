package com.msg.gauth.global.security.jwt

import com.msg.gauth.global.security.auth.AuthDetailsService
import com.msg.gauth.global.security.exception.ExpiredTokenException
import com.msg.gauth.global.security.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.security.Key

@Component
class TokenParser(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService
) {

    fun parseToken(token: String): String? =
        if (token.startsWith(JwtTokenProvider.TOKEN_PREFIX)) token.replace(JwtTokenProvider.TOKEN_PREFIX, "") else null

    fun exactEmailFromRefreshToken(refresh: String): String {
        return getTokenSubject(refresh, jwtProperties.refreshSecret)
    }

    fun exactEmailFromOauthRefreshToken(refresh: String): String =
        getTokenSubject(refresh, jwtProperties.oauthSecret)

    fun exactClientIdFromOauthRefreshToken(refresh: String): String =
        getTokenBody(refresh, jwtProperties.oauthSecret)["clientId"].toString()

    fun authentication(token: String): Authentication {
        val userDetails = authDetailsService.loadUserByUsername(getTokenSubject(token, jwtProperties.accessSecret))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

    private fun getTokenBody(token: String, secret: Key): Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (e: Exception) {
            throw InvalidTokenException()
        }
    }

    private fun getTokenSubject(token: String, secret: Key): String =
        getTokenBody(token, secret).subject
}