package com.lablabla.meow.data.remote.dto

import com.lablabla.meow.R
import com.lablabla.meow.domain.model.MeowUser

data class FirestoreUserDto(
    val id: String? = null,
    val email: String? = null,
    val name: String? = null,
    val phone: String? = null,
    val token: String? = null,
    val references: List<String> = emptyList()
) {
    fun toMeowUser(): MeowUser {
        return MeowUser(name = name ?: "", token = token ?: "", com.google.firebase.firestore.R.drawable.googleg_standard_color_18)
    }
}