package com.lablabla.meow.presentation.data

import com.lablabla.meow.domain.model.MeowUser

data class MeowUsersState(
    val meowUsers: List<MeowUser> = emptyList(),
    val isLoading: Boolean = false
)
