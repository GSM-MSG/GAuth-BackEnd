package com.msg.gauth.domain.user.specification

import com.msg.gauth.domain.user.User
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

class UserSpecification {
    companion object{
        fun equalGrade(grade: Int): Specification<User> {
            return Specification {
                    root: Root<User>, _: CriteriaQuery<*>?, criteriaBuilder: CriteriaBuilder ->
                criteriaBuilder.equal(root.get<Any>("grade"), grade)
            }
        }

        fun equalClassNum(classNum: Int): Specification<User> {
            return Specification {
                    root: Root<User>, _: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
                criteriaBuilder.equal(root.get<Any>("classNum"), classNum)
            }
        }

        fun containKeyword(keyword: String): Specification<User> {
            return Specification {
                    root: Root<User>, _: CriteriaQuery<*>, criteriaBuilder: CriteriaBuilder ->
                criteriaBuilder.like(root.get("name"), "%${keyword}%")
            }
        }
    }
}