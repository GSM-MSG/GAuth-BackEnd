package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.domain.oauth.OauthCode
import org.springframework.data.repository.CrudRepository

interface OauthCodeRepository: CrudRepository<OauthCode, String> {
}