package com.msg.gauth.domain.user.repository

import com.msg.gauth.domain.user.QUser.user
import com.msg.gauth.domain.user.QUserRole.userRole
import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRoleType
import com.msg.gauth.domain.user.enums.UserState
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.PathBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomUserRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomUserRepository {

    override fun search(grade: Int, classNum: Int, keyword: String): List<User> {
        val gradeEq = gradeEq(grade)
        val classNumEq = classNumEq(classNum)
        val stateEq = stateEq(UserState.CREATED)
        val roleEq = roleEq(UserRoleType.ROLE_STUDENT)

        val pathBuilder = PathBuilder(user.type, user.metadata)
        val orderSpecifiers = mutableListOf<OrderSpecifier<*>>()

        val query = jpaQueryFactory.selectFrom(user)
            .leftJoin(userRole).on(userRole.user.eq(user)).fetchJoin()
            .where(
                gradeEq,
                classNumEq,
                stateEq,
                roleEq,
                keywordLike(keyword)
            )

        if (gradeEq == null) {
            orderSpecifiers.add(OrderSpecifier(Order.ASC, pathBuilder.get(user.grade)))
        }

        if (classNumEq == null) {
            orderSpecifiers.add(OrderSpecifier(Order.ASC, pathBuilder.get(user.classNum)))
        }

        orderSpecifiers.add(OrderSpecifier(Order.ASC, pathBuilder.get(user.num)))

        return query
            .orderBy(*orderSpecifiers.toTypedArray()).fetch()
    }

    override fun findAllByUserRoleType(userRoleType: UserRoleType): List<User> {
        val roleEq = roleEq(userRoleType)

        return jpaQueryFactory.selectFrom(user)
            .leftJoin(userRole).on(userRole.user.eq(user)).fetchJoin()
            .where(
                roleEq
            ).fetch()
    }

    private fun gradeEq(grade: Int): BooleanExpression? =
        if (grade != 0) user.grade.eq(grade) else null

    private fun classNumEq(classNum: Int): BooleanExpression? =
        if (classNum != 0) user.classNum.eq(classNum) else null

    private fun keywordLike(keyword: String): BooleanExpression? =
        if (keyword.isNotEmpty()) user.name.like("%$keyword%") else null

    private fun roleEq(userRoleType: UserRoleType): BooleanExpression =
        userRole.userRoleType.eq(userRoleType)

    private fun stateEq(userState: UserState): BooleanExpression =
        user.state.eq(userState)
}