package com.lablabla.meow.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.domain.model.MeowUser
import com.lablabla.meow.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    useCases: UseCases
) : BaseViewModel(useCases) {


    override fun onEvent(event: Event) {
        when (event) {
            is LoginEvent.Login -> {
                viewModelScope.launch {
                    login(event.user)
                }
            }
        }
    }

    open class LoginEvent : Event() {
        data class Login(val user: GoogleSignInAccount): LoginEvent()
    }

    private var loginJob: Job? = null
    private fun login(account: GoogleSignInAccount) {
        loginJob?.cancel()
        loginJob = viewModelScope.launch {
            useCases.login(account).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _eventFlow.emit(UIEvent.ShowSnackbar("Logged in as ${account.displayName}"))
                    }
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {


                    }
                }

            }
        }
    }
}