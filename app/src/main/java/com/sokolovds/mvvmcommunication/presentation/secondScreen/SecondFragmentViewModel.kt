package com.sokolovds.mvvmcommunication.presentation.secondScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokolovds.mvvmcommunication.presentation.utils.navigationHandler.NavigationController

class SecondFragmentViewModel(
    private val navigationController: NavigationController
) : ViewModel() {
    val navActionFlow = navigationController.navActionFlow(viewModelScope)

    fun onBackPressed() {
        navigationController.back()
    }
}