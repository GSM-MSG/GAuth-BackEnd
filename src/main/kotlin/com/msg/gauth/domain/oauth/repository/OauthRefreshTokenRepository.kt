package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.global.util.count.oauth.OauthRefreshToken
import org.springframework.data.repository.CrudRepository

interface OauthRefreshTokenRepository : CrudRepository<OauthRefreshToken, Long>