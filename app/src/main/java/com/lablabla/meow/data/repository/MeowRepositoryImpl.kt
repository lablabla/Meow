package com.lablabla.meow.data.repository

import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.data.local.MeowUserDao
import com.lablabla.meow.data.remote.MeowApi
import com.lablabla.meow.domain.model.MeowUser
import com.lablabla.meow.domain.repository.MeowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MeowRepositoryImpl(
    private val api: MeowApi,
    private val dao: MeowUserDao
): MeowRepository {
    override fun getUsers(): Flow<Resource<List<MeowUser>>> = flow {
        emit(Resource.Loading())

        val users = dao.getMeowUsers().map { it.toMeowUser() }
        emit(Resource.Loading(data = users))

        // Try get from remote to update new users
        try {
            val remoteUsers = api.getUsers()
            dao.deleteAllUsers()
            dao.insertMeowUsers(remoteUsers.map { it.toEntity() })
        } catch (e: Exception) {
            emit(Resource.Error(data = users, message = e.localizedMessage ?: "Unknown error occurred"))
        }


        val newUsers = dao.getMeowUsers().map { it.toMeowUser() }
        emit(Resource.Success(newUsers))
    }
}