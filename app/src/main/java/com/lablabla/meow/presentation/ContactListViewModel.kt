package com.lablabla.meow.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.domain.model.MeowUser
import com.lablabla.meow.domain.use_case.GetUsers
import com.lablabla.meow.domain.use_case.SendMeow
import com.lablabla.meow.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = mutableStateOf(MeowUsersState())
    val state: State<MeowUsersState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

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
                        _eventFlow.emit(UIEvent.ShowSnackbar(
                            result.message ?: "Unknown error"
                        ))
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

    fun onEvent(event: Event) {
        when(event) {
            is Event.SendMeow -> {
                viewModelScope.launch {
                    _eventFlow.emit(UIEvent.ShowSnackbar("Sending to user ${event.user.name}"))
                    sendMeow(event.user)
                }
            }
        }
    }

    private var sendMeowJob: Job? = null
    private fun sendMeow(meowUser: MeowUser) {
        sendMeowJob?.cancel()
        sendMeowJob = viewModelScope.launch {
            delay(500L)
            useCases.sendMeow(meowUser)
        }
    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }

    sealed class Event {
        data class SendMeow(val user: MeowUser): Event()
    }
}