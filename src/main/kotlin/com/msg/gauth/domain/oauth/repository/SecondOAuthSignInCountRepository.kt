package com.msg.gauth.domain.oauth.repository

import com.msg.gauth.global.util.count.oauth.SecondOAuthSignInCount
import org.springframework.data.repository.CrudRepository

interface SecondOAuthSignInCountRepository : CrudRepository<SecondOAuthSignInCount, String>