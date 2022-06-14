package com.sokolovds.mvvmcommunication.presentation.utils.stateHandler

import androidx.lifecycle.LifecycleCoroutineScope
import com.sokolovds.mvvmcommunication.domain.ApplicationError
import com.sokolovds.mvvmcommunication.presentation.utils.ViewHandler
import kotlinx.coroutines.flow.StateFlow


abstract class StateHandler<T>(
    private val lifecycleScope: LifecycleCoroutineScope,
    private val stateFlow: StateFlow<State<T>>,
    private val handler: HandlerImplementation<T>
) : ViewHandler {

    interface HandlerImplementation<T> {
        fun onSuccessState(data: T)
        fun onErrorState(error: ApplicationError)
        fun onLoadingState()
        fun setupStartConfiguration()
    }

    private var isSubscribe = false

    override fun subscribe() {
            lifecycleScope.launchWhenStarted {
                stateFlow.collect { event ->
                    handleState(event)
                }
        }
    }

    private fun handleState(state: State<T>) {
        when (state) {
            is State.Success<T> -> {
                handler.onSuccessState(state.data)
            }
            is State.Loading -> {
                handler.onLoadingState()
            }
            is State.Error -> {
                handler.onErrorState(state.error)
            }
            is State.StartingConfiguration -> {
                handler.setupStartConfiguration()
            }
        }
    }

    class Base<T>(
        lifecycleScope: LifecycleCoroutineScope,
        stateFlow: StateFlow<State<T>>,
        handler: HandlerImplementation<T>
    ) : StateHandler<T>(lifecycleScope, stateFlow, handler)
}


