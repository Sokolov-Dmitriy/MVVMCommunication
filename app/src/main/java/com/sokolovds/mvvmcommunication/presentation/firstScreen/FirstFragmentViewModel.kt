package com.sokolovds.mvvmcommunication.presentation.firstScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sokolovds.mvvmcommunication.domain.onError
import com.sokolovds.mvvmcommunication.domain.onLoading
import com.sokolovds.mvvmcommunication.domain.onSuccess
import com.sokolovds.mvvmcommunication.domain.usecases.GetHumanFromGenerator
import com.sokolovds.mvvmcommunication.domain.usecases.GetRandomHuman
import com.sokolovds.mvvmcommunication.presentation.utils.navigationHandler.NavigationController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FirstFragmentViewModel(
    private val getRandomHuman: GetRandomHuman,
    private val getHumanFromGenerator: GetHumanFromGenerator,
    private val navigationController: NavigationController
) : ViewModel() {

    private val randomHumanController = getRandomHuman.controller
    val randomHumanState = randomHumanController.stateFlow

    private val humanFromGeneratorController = getHumanFromGenerator.controller
    val humanFromGeneratorState = humanFromGeneratorController.stateFlow

    val navActionFlow = navigationController.navActionFlow(viewModelScope)

    init {
        println("STATE:ViewModel:init")
    }

    fun loadRandomHuman() {
        viewModelScope.launch {
            randomHumanController.loadingState()
            getRandomHuman()
                .onSuccess { randomHumanController.successState(it) }
                .onError { }
        }
    }

    fun startGenerateHuman() {
        viewModelScope.launch {
            getHumanFromGenerator().collectLatest { result ->
                result
                    .onSuccess { humanFromGeneratorController.successState(it) }
                    .onLoading { humanFromGeneratorController.loadingState() }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("STATE:ViewModel:onCleared")
    }

    fun onSecondFragmentBtnPressed() {
        navigationController.navigateTo(FirstFragmentDirections.actionFirstFragmentToSecondFragment())
    }
}