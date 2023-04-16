package com.msg.gauth.domain.user.repository

import org.springframework.stereotype.Repository
import javax.persistence.EntityManager

@Repository
class CustomUserRepositoryImpl(private val entityManager: EntityManager): CustomUserRepository {

    override fun searchUser(grade: Int, classNum: Int, keyword: String) {
      TODO("Asdf")
    }

}