package com.msg.gauth.domain.auth.repository

import com.msg.gauth.domain.auth.TempSignInBan
import org.springframework.data.repository.CrudRepository

interface TempSignInBanRepository : CrudRepository<TempSignInBan, String> {
}