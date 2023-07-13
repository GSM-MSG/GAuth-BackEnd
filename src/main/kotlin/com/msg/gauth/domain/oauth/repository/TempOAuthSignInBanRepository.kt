package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.global.util.count.oauth.TempOAuthSignInBan
import org.springframework.data.repository.CrudRepository

interface TempOAuthSignInBanRepository : CrudRepository<TempOAuthSignInBan, String> {
}