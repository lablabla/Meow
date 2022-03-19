package com.lablabla.meow.presentation

import com.lablabla.meow.domain.model.MeowUser

data class MeowUsersState(
    val meowUsers: List<MeowUser> = emptyList(),
    val isLoading: Boolean = false
)
