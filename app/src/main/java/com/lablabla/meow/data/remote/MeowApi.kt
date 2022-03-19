package com.lablabla.meow.data.remote

import com.lablabla.meow.domain.model.MeowUser

interface MeowApi {
    suspend fun getUsers(): List<MeowUser>
}