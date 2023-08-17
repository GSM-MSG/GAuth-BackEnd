package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.QUser.user
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRole
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomUserRepository {

    override fun search(grade: Int, classNum: Int, keyword: String): List<User> {

        return jpaQueryFactory.selectFrom(user)
                .where(
                    gradeEq(grade),
                    classNumEq(classNum),
                    keywordLike(keyword),
                    roleContains(UserRole.ROLE_STUDENT)
                )
                .fetch()
    }

    private fun gradeEq(grade: Int): BooleanExpression? =
        if(grade != 0) user.grade.eq(grade) else null

    private fun classNumEq(classNum: Int): BooleanExpression? =
        if(classNum != 0) user.classNum.eq(classNum) else null

    private fun keywordLike(keyword: String): BooleanExpression? =
        if(keyword.isNotEmpty()) user.name.like("%${keyword}%") else null

    private fun roleContains(userRole: UserRole): BooleanExpression =
        user.roles.contains(userRole)

}