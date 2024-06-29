package com.msg.gauth.domain.auth.repository

import com.msg.gauth.global.util.count.auth.MinuteSignInCount
import org.springframework.data.repository.CrudRepository

interface MinuteSignInCountRepository : CrudRepository<MinuteSignInCount, String>