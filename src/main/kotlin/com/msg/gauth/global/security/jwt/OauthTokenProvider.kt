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
    private val jwtProperties: JwtProperties
) {

    companion object {
        const val OAUTH_ACCESS_TYPE = "access"
        const val OAUTH_REFRESH_TYPE = "refresh"
        const val OAUTH_ACCESS_EXP = 60L * 15 // 15 min
        const val OAUTH_REFRESH_EXP = 60L * 60 * 24 * 7 // 1 weeks
    }

    fun generateOauthAccessToken(email: String, clientId: String): String =
        generateOauthToken(email,
            OAUTH_ACCESS_TYPE, clientId, jwtProperties.oauthSecret,
            OAUTH_ACCESS_EXP
        )

    fun generateOauthRefreshToken(email: String, clientId: String): String =
        generateOauthToken(email,
            OAUTH_REFRESH_TYPE, clientId, jwtProperties.oauthSecret,
            OAUTH_REFRESH_EXP
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

}

