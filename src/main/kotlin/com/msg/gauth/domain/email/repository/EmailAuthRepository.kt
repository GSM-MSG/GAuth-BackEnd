package com.msg.gauth.domain.email.repository

import com.msg.gauth.domain.email.EmailAuthEntity
import org.springframework.data.repository.CrudRepository

interface EmailAuthRepository: CrudRepository<EmailAuthEntity, String>