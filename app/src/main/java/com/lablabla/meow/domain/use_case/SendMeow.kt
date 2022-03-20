package com.lablabla.meow.domain.use_case

import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.domain.model.MeowUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendMeow {

    suspend operator fun invoke(meowUser: MeowUser) : Flow<Resource<MeowUser>> = flow {
        try {
            // Send actual meow
            emit(Resource.Success(data = meowUser))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.localizedMessage ?: "Failed sending meow"))
        }
    }
}