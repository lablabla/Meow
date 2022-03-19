package com.lablabla.meow.data.repository

import com.lablabla.meow.R
import com.lablabla.meow.data.remote.MeowApi
import com.lablabla.meow.domain.model.MeowUser

class MeowApiImpl: MeowApi {
    override suspend fun getUsers(): List<MeowUser> {
        return listOf(
            MeowUser("Test1", "token1", R.drawable.ic_launcher_background),
            MeowUser("Test2", "token2", R.drawable.ic_launcher_foreground)
        )
    }
}