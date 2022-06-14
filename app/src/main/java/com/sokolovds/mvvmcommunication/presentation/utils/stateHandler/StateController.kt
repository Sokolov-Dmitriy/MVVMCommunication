package com.sokolovds.mvvmcommunication.presentation.utils.stateHandler

import com.sokolovds.mvvmcommunication.domain.ApplicationError
import com.sokolovds.mvvmcommunication.domain.mappers.MapperToUI
import kotlinx.coroutines.flow.*

interface StateController<DOMAIN, UI> {
    fun <DOMAIN : MapperToUI<UI>> successState(data: DOMAIN)
    fun loadingState()
    fun startingState()
    fun errorState(error: ApplicationError)
    val stateFlow: StateFlow<State<UI>>

    class StateControllerImpl<DOMAIN, UI> : StateController<DOMAIN, UI> {
        override val stateFlow
            get() = _stateFlow.asStateFlow()
        private val _stateFlow = MutableStateFlow<State<UI>>(State.StartingConfiguration)

        override fun <DOMAIN : MapperToUI<UI>> successState(data: DOMAIN) {
            _stateFlow.value = State.Success(data.toUIEntity())
        }

        override fun loadingState() {
            _stateFlow.value = State.Loading
        }

        override fun startingState() {
            _stateFlow.value = State.StartingConfiguration
        }

        override fun errorState(error: ApplicationError) {
            _stateFlow.value = State.Error(error)
        }
    }
}

