package com.lablabla.meow.domain.use_case

data class UseCases(
    // User list
    val getUsers: GetUsers,
    val sendMeow: SendMeow,

    // Login
    val login: Login
)