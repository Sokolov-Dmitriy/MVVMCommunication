package com.sokolovds.mvvmcommunication.presentation.utils.stateHandler

import com.sokolovds.mvvmcommunication.domain.ApplicationError

sealed class State<out T> {
    data class Success<out R>(val data: R) : State<R>()
    object Loading : State<Nothing>()
    data class Error(val error: ApplicationError) : State<Nothing>()
    object StartingConfiguration : State<Nothing>()
}