package com.msg.gauth.domain.user.repository

import com.fasterxml.jackson.databind.util.ArrayBuilders
import com.msg.gauth.domain.user.QUser.user
import com.msg.gauth.domain.user.User
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
class CustomUserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomUserRepository {

    override fun searchUser(grade: Int, classNum: Int, keyword: String): List<User> {
        val booleanBuilder = BooleanBuilder()

        if (grade != 0) {
            booleanBuilder.and(user.grade.eq(grade))
        }

        if (classNum != 0) {
            booleanBuilder.and(user.classNum.eq(classNum))
        }

        if (keyword.isNotEmpty()) {
            booleanBuilder.and(user.name.like("%$keyword%"))
        }

        return jpaQueryFactory.selectFrom(user)
            .where(booleanBuilder)
            .fetch()
    }

}