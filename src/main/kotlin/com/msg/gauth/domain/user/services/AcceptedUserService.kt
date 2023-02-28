package com.msg.gauth.domain.user.services

import com.msg.gauth.domain.user.User
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.presentation.dto.response.SingleAcceptedUserResDto
import com.msg.gauth.domain.user.repository.UserRepository
import com.msg.gauth.domain.user.specification.UserSpecification
import com.msg.gauth.global.annotation.service.ReadOnlyService
import org.springframework.data.jpa.domain.Specification
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

@ReadOnlyService
class AcceptedUserService(
    private val userRepository: UserRepository,
) {
    fun execute(grade: Int, classNum: Int, keyword: String): List<SingleAcceptedUserResDto> {

        val users: List<User>
        = if(grade == 0 && classNum == 0 && keyword == "0")
            listAll()
        else {
            search(grade, classNum, keyword)
        }

       return users.filter { user -> user.roles.equals(UserRole.ROLE_STUDENT) }
            .map { user ->
                SingleAcceptedUserResDto(
                    user.id,
                    user.name!!,
                    user.email,
                    user.grade!!,
                    user.classNum!!,
                    user.num!!,
                    user.profileUrl
                )
            }

    }

    private fun listAll(): List<User> =
        userRepository.findAll()

    private fun search(grade: Int, classNum: Int, keyword: String): List<User> {
        val spec: Specification<User> =
            Specification { _: Root<User>?, _: CriteriaQuery<*>?, _: CriteriaBuilder? -> null }

        if(grade != 0)
            spec.and(UserSpecification.equalGrade(grade))
        if(classNum != 0)
            spec.and(UserSpecification.equalGrade(classNum))
        if(keyword != "0")
            spec.and(UserSpecification.containKeyword(keyword))

        return userRepository.findAll(spec)
    }
}