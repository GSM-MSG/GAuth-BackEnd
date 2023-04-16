package com.msg.gauth.global.querydsl

import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Configuration
class QuerydslConfig(
    @PersistenceContext
    private val entityManager: EntityManager
) {

    @Bean
    fun JPAQueryFactory(): JPAQueryFactory = JPAQueryFactory(entityManager)
}

fun <T> QuerydslPredicateExecutor<T>.findBy(vararg expressions: BooleanExpression?): List<T> {

    var predicate = expressions[0]!!
    expressions.forEach { predicate = predicate.and(it) }

    return findAll(predicate).toList()
}