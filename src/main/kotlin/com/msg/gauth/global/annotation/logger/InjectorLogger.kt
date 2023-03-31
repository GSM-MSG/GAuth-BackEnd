package com.msg.gauth.global.annotation.logger

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class InjectorLogger{
    @Around("@annotation(com.msg.gauth.global.annotation.logger.log4k)")
    fun inject(joinPoint: ProceedingJoinPoint): Any?{
        println("진짜 테스트")
        val target = joinPoint.target
        val fields = target.javaClass.declaredFields
        fields.filter { it.type == Logger::class.java }
            .forEach {
                it.isAccessible = true
                it.set(target, LoggerFactory.getLogger(target::class.simpleName))
            }
        return joinPoint.proceed()
    }
}