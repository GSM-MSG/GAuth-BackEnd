package com.msg.gauth.global.entity

import javax.persistence.*

@MappedSuperclass
abstract class BaseIdEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = false)
    open val id: Long
)