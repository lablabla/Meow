package com.lablabla.meow.domain.model

import com.lablabla.meow.data.local.entity.MeowUserEntity

data class MeowUser(
    val name: String,
    val token: String,
    val drawable: Int)
{
    fun toEntity(): MeowUserEntity {
        return MeowUserEntity(
            name = name,
            token = token,
            drawable = drawable
        )
    }
}