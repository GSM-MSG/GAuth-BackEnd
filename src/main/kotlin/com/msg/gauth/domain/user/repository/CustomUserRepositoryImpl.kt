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

    override fun search(grade: Int, classNum: Int, keyword: String): List<User> {

        val expression = (if (grade != 0) user.grade.eq(grade) else null)
            ?.and(if (classNum != 0) user.classNum.eq(classNum) else null)
            ?.and(if (keyword.isNotEmpty()) user.name.like("%$keyword%") else null)

        return if(expression == null){
            jpaQueryFactory.selectFrom(user)
                .fetch()
        } else {
            jpaQueryFactory.selectFrom(user)
                .where(expression)
                .fetch()
        }
    }

}