package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.domain.oauth.TempOAuthSignInBan
import org.springframework.data.repository.CrudRepository

interface TempOAuthSignInBanRepository : CrudRepository<TempOAuthSignInBan, String> {
}