package com.lablabla.meow.domain.use_case

import com.google.firebase.auth.FirebaseAuth
import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.domain.model.MeowUser
import com.lablabla.meow.domain.repository.MeowRepository
import kotlinx.coroutines.flow.Flow

class GetUsers(
    private val repository: MeowRepository
) {

    operator fun invoke() : Flow<Resource<List<MeowUser>>> {
        return repository.getUsers()
    }
}