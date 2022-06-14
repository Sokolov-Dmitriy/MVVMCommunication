package com.sokolovds.mvvmcommunication.presentation.utils.navigationHandler

import androidx.navigation.NavDirections

sealed class NavigationAction {
    data class ToDirection(val directions: NavDirections) : NavigationAction()
    object Back : NavigationAction()
}