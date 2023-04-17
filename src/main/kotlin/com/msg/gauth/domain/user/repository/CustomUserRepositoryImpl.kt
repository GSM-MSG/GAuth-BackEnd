package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.User
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl : CustomUserRepository {

    override fun searchUser(grade: Int, classNum: Int, keyword: String) {
    }
}