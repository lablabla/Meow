package com.lablabla.meow.domain.repository

import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.domain.model.MeowUser
import kotlinx.coroutines.flow.Flow

interface MeowRepository {
    fun getUsers(): Flow<Resource<List<MeowUser>>>
}