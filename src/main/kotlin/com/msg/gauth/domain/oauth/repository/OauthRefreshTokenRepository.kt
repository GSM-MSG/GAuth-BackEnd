package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.domain.oauth.OauthRefreshToken
import org.springframework.data.repository.CrudRepository

interface OauthRefreshTokenRepository: CrudRepository<OauthRefreshToken, Long> {
}