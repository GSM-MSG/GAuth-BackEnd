package com.msg.gauth.global.security.jwt

import com.msg.gauth.global.security.auth.AuthDetailsService
import com.msg.gauth.global.security.exception.ExpiredTokenException
import com.msg.gauth.global.security.exception.InvalidTokenException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class OauthTokenProvider(
    private val jwtProperties: JwtProperties,
    private val authDetailsService: AuthDetailsService,
    private val jwtTokenProvider: JwtTokenProvider
) {

    companion object {
        const val ACCESS_TYPE = "access"
        const val REFRESH_TYPE = "refresh"
        const val ACCESS_EXP = 60L * 15 // 15 min
        const val REFRESH_EXP = 60L * 60 * 24 * 7 // 1 weeks
        const val TOKEN_PREFIX = "Bearer "
    }

    fun generateOauthAccessToken(email: String, clientId: String): String =
        generateOauthToken(email,
            ACCESS_TYPE, clientId, jwtProperties.oauthSecret,
            ACCESS_EXP
        )

    fun generateOauthRefreshToken(email: String, clientId: String): String =
        generateOauthToken(email,
            REFRESH_TYPE, clientId, jwtProperties.oauthSecret,
            REFRESH_EXP
        )

    private fun generateOauthToken(sub: String, type: String, clientId: String, secret: Key, exp: Long): String =
        Jwts.builder()
            .signWith(secret, SignatureAlgorithm.HS256)
            .setSubject(sub)
            .claim("clientId", clientId)
            .claim("type", type)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + exp * 1000))
            .compact()

    fun parseToken(token: String): String? =
        if (token.startsWith(JwtTokenProvider.TOKEN_PREFIX)) token.replace(JwtTokenProvider.TOKEN_PREFIX, "") else null

    fun exactEmailFromOauthRefreshToken(refresh: String): String =
        getTokenSubject(refresh, jwtProperties.oauthSecret)

    fun exactClientIdFromOauthRefreshToken(refresh: String): String =
        getTokenBody(refresh, jwtProperties.oauthSecret)["clientId"].toString()

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

