package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.global.util.count.oauth.MinuteOAuthSignInCount
import org.springframework.data.repository.CrudRepository

interface MinuteOAuthSignInCountRepository : CrudRepository<MinuteOAuthSignInCount, String>