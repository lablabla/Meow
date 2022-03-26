package com.lablabla.meow.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.lablabla.meow.domain.use_case.UseCases
import com.lablabla.meow.presentation.data.MeowUsersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    protected val useCases: UseCases
) : ViewModel() {

    protected val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    open class Event
    open fun onEvent(event: Event) {

    }

    sealed class UIEvent {
        data class ShowSnackbar(val message: String): UIEvent()
    }

}