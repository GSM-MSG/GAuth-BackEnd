package com.msg.gauth.domain.user

import com.msg.gauth.domain.user.enums.Gender
import com.msg.gauth.domain.user.enums.UserRole
import com.msg.gauth.domain.user.enums.UserState
import com.msg.gauth.global.entity.BaseIdEntity
import javax.persistence.*
import javax.validation.constraints.Size

@Entity
class User(

    override val id: Long = 0,

    @Column(unique = true)
    val email: String,

    @field:Size(max = 60)
    val password: String,

    @Enumerated(EnumType.STRING)
    val gender: Gender? = null,

    @Column(nullable = true)
    val name: String? = null,

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

    @Column(nullable = true, columnDefinition = "TEXT")
    val profileUrl: String?,
): BaseIdEntity(id){

}