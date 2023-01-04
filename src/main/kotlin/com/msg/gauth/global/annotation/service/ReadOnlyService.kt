package com.msg.gauth.global.annotation.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
annotation class ReadOnlyService()
