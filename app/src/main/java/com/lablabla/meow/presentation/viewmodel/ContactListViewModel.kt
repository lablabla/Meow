package com.lablabla.meow.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.domain.model.MeowUser
import com.lablabla.meow.domain.use_case.UseCases
import com.lablabla.meow.presentation.data.MeowUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    useCases: UseCases
) : BaseViewModel(useCases) {

    private val _state = mutableStateOf(MeowUsersState())
    val state: State<MeowUsersState> = _state

    init {
        viewModelScope.launch {
            useCases.getUsers().onEach { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            meowUsers = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            meowUsers = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackbar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            meowUsers = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    override fun onEvent(event: Event) {
        when (event) {
            is ContactListEvent.SendMeow -> {
                viewModelScope.launch {
                    _eventFlow.emit(UIEvent.ShowSnackbar("Sending to user ${event.user.name}"))
                    sendMeow(event.user)
                }
            }
        }
    }

    open class ContactListEvent : Event() {
        data class SendMeow(val user: MeowUser): ContactListEvent()
    }

    private var sendMeowJob: Job? = null
    private fun sendMeow(meowUser: MeowUser) {
        sendMeowJob?.cancel()
        sendMeowJob = viewModelScope.launch {
            delay(500L)
            useCases.sendMeow(meowUser).onEach {

            }.launchIn(this)
        }
    }
}