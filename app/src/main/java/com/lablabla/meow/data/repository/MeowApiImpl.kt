package com.lablabla.meow.data.repository

import com.lablabla.meow.data.remote.FirebaseSource
import com.lablabla.meow.data.remote.MeowApi
import com.lablabla.meow.data.remote.dto.FirestoreUserDto
import com.lablabla.meow.data.util.Utils
import com.lablabla.meow.domain.model.MeowUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MeowApiImpl @Inject constructor(
    private val firebaseSource: FirebaseSource
): MeowApi {
    override suspend fun getUsers(): List<MeowUser> {
        val userTask = firebaseSource.fetchUser()?.await()
        userTask?.data.let {
            val firestoreUser = Utils.mapToObject(it!!, FirestoreUserDto::class)

            if (firestoreUser.references.isEmpty())
            {
                return emptyList()
            }

            val list = arrayListOf<MeowUser>()
            val allUsers = firebaseSource.fetchAllUsers()?.await()!!
            firestoreUser.references.forEach { reference ->
                allUsers.documents.forEach { document ->
                    document.data?.let { it1 ->
                        val remoteFirestoreUser = Utils.mapToObject(it1, FirestoreUserDto::class)
                        if (reference == remoteFirestoreUser.id) {
                            val meowUser = remoteFirestoreUser.toMeowUser()
                            list.add(meowUser)
                        }
                    }
                }
                return list
            }

        }
        return emptyList()
    }
}