package com.msg.gauth.domain.client.repository

import com.msg.gauth.domain.user.User

interface CustomClientRepository {
    fun deleteAllByIdsAndCreatedBy(ids: List<Long>, createdBy: User)
}