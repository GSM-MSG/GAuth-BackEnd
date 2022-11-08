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

    password: String,

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "UserRole", joinColumns = [JoinColumn(name = "id")])
    var roles: MutableList<UserRole> = mutableListOf(),

    @Enumerated(EnumType.STRING)
    var state: UserState,

): BaseIdEntity(){
    @Enumerated(EnumType.STRING)
    var gender: Gender? = null
    private set

    @field:Size(max = 60)
    var password: String = password
    private set

    @Column(nullable = true)
    var grade: Int? = null
    private set

    @Column(nullable = true)
    var classNum: Int? = null
    private set

    @Column(nullable = true)
    var num: Int? = null
    private set
}