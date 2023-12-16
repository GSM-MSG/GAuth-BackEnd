package com.msg.gauth.domain.user

import com.msg.gauth.global.entity.BaseIdEntity
import javax.persistence.*
import com.msg.gauth.domain.user.enums.UserRoleType

@Entity
class UserRoleEntity(
    override val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    val userRoleType: UserRoleType
) : BaseIdEntity(id)