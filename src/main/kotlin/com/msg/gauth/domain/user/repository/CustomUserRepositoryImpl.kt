package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.QUser.user
import com.msg.gauth.domain.user.QUserRole
import com.msg.gauth.domain.user.QUserRole.userRole
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRoleType
import com.msg.gauth.domain.user.enums.UserState
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomUserRepository {

    override fun search(grade: Int, classNum: Int, keyword: String): List<User> {
        return jpaQueryFactory.selectFrom(user)
            .leftJoin(user.userRoles, userRole).fetchJoin()
            .where(
                gradeEq(grade),
                classNumEq(classNum),
                keywordLike(keyword),
                stateEq(UserState.CREATED),
                userRole.userRoleType.eq(UserRoleType.ROLE_STUDENT)
            ).fetch()
    }

    private fun gradeEq(grade: Int): BooleanExpression? =
        if(grade != 0) user.grade.eq(grade) else null

    private fun classNumEq(classNum: Int): BooleanExpression? =
        if(classNum != 0) user.classNum.eq(classNum) else null

    private fun keywordLike(keyword: String): BooleanExpression? =
        if(keyword.isNotEmpty()) user.name.like("%${keyword}%") else null

    private fun roleContains(userRoleType: UserRoleType): BooleanExpression =
        user.roles.contains(userRoleType)

    private fun stateEq(userState: UserState): BooleanExpression =
        user.state.eq(userState)
}