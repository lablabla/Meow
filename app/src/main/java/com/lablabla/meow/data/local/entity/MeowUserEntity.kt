package com.lablabla.meow.data.local.entity

import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.lablabla.meow.domain.model.MeowUser

@Entity
data class MeowUserEntity(
    val name: String,
    val token: String,
    @DrawableRes val drawable: Int,
    @PrimaryKey val id: Int? = null
) {
    fun toMeowUser(): MeowUser {
        return MeowUser(
            name = name,
            token = token,
            drawable = drawable
        )
    }
}