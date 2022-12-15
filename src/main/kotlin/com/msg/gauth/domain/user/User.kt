package com.msg.gauth.domain.user

import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.global.entity.BaseIdEntity
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
class User(

    @Column(unique = true)
    val email: String,

    @field:Size(max = 60)
    val password: String,

    @Enumerated(EnumType.STRING)
    val gender: Gender? = null,

    @Column(nullable = true)
    val grade: Int? = null,

    @Column(nullable = true)
    val classNum: Int? = null,

    @Column(nullable = true)
    val num: Int? = null,

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "UserRole", joinColumns = [JoinColumn(name = "id")])
    val roles: MutableList<UserRole> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    val state: UserState,

    @Column(nullable = true)
    val profileUrl: String?,
): BaseIdEntity(){
    fun update(grade: Int, classNum: Int, num: Int, gender: Gender): User{
        val user = User(
            email = this.email,
            grade = grade,
            classNum = classNum,
            num = num,
            gender = gender,
            password = this.password,
            roles = this.roles,
            state = UserState.CREATED,
            profileUrl = this.profileUrl
        )
        user.id = this.id
        return user
    }

    fun update(password: String): User{
        val user = User(
            email = this.email,
            grade = this.grade,
            classNum = this.classNum,
            num = this.num,
            gender = this.gender,
            password = password,
            roles = this.roles,
            state = this.state,
            profileUrl = this.profileUrl
        )
        user.id = this.id
        return user
    }

    fun updateProfile(profileUrl: String): User{
        val user = User(
            email = this.email,
            grade = this.grade,
            classNum = this.classNum,
            num = this.num,
            gender = this.gender,
            password = this.password,
            roles = this.roles,
            state = this.state,
            profileUrl = profileUrl
        )
        user.id = this.id
        return user
    }

}