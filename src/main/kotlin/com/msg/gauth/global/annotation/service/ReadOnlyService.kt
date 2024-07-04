package com.msg.gauth.global.annotation.service

import org.springframework.core.annotation.AliasFor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.reflect.KClass

@Service
@Transactional(readOnly = true, rollbackFor = [Exception::class])
annotation class ReadOnlyService(
    @get: AliasFor(annotation = Transactional::class)
    val noRollbackFor: Array<KClass<out Throwable>> = []
)