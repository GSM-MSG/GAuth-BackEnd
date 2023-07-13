package com.msg.gauth.domain.auth.repository

import com.msg.gauth.global.util.count.auth.SecondSignInCount
import org.springframework.data.repository.CrudRepository

interface SecondSignInCountRepository : CrudRepository<SecondSignInCount, String> {
}