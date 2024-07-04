package com.msg.gauth.domain.client.repository

import com.msg.gauth.domain.client.QClient.client
import com.msg.gauth.domain.user.User
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class CustomClientRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory
) : CustomClientRepository {

    override fun deleteAllByIdsAndCreatedBy(ids: List<Long>, createdBy: User) {
        jpaQueryFactory.delete(client)
            .where(client.id.`in`(ids), client.createdBy.eq(createdBy))
            .execute()
    }
}