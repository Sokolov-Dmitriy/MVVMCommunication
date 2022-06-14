package com.sokolovds.mvvmcommunication.domain

import com.sokolovds.mvvmcommunication.presentation.utils.stateHandler.StateController

interface UseCase<DOMAIN,UI> {
    val controller: StateController<DOMAIN,UI>
        get() = StateController.StateControllerImpl()
}