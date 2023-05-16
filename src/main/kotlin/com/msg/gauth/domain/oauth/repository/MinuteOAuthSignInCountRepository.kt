package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.domain.oauth.MinuteOAuthSignInCount
import org.springframework.data.repository.CrudRepository

interface MinuteOAuthSignInCountRepository : CrudRepository<MinuteOAuthSignInCount, String> {
}