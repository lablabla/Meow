package com.lablabla.meow.domain.use_case

import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.domain.model.MeowUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SendMeow {

    operator fun invoke() : Flow<Resource<MeowUser>> {
        TODO("Unimplemented")
        return flow {}
    }
}