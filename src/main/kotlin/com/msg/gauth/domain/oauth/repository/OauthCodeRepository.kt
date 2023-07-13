package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.global.util.count.oauth.OauthCode
import org.springframework.data.repository.CrudRepository

interface OauthCodeRepository: CrudRepository<OauthCode, String> {
}