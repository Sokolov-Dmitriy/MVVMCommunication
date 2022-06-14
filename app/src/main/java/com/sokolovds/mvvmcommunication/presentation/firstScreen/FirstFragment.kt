package com.sokolovds.mvvmcommunication.presentation.firstScreen

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sokolovds.mvvmcommunication.R
import com.sokolovds.mvvmcommunication.databinding.FirstFragmentBinding
import com.sokolovds.mvvmcommunication.di.ViewHandlerEnum
import com.sokolovds.mvvmcommunication.domain.ApplicationError
import com.sokolovds.mvvmcommunication.presentation.entities.HumanUIEntity
import com.sokolovds.mvvmcommunication.presentation.utils.stateHandler.StateHandler
import com.sokolovds.mvvmcommunication.presentation.utils.ViewHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named


class FirstFragment : Fragment(R.layout.first_fragment) {
    private val binding by viewBinding(FirstFragmentBinding::bind)
    private val viewModel by viewModel<FirstFragmentViewModel>()

    private val randomHumanHandler by inject<ViewHandler>(named(ViewHandlerEnum.RANDOM_HUMAN)) {
        parametersOf(
            lifecycleScope,
            viewModel.randomHumanState,
            object : StateHandler.HandlerImplementation<HumanUIEntity> {
                override fun onSuccessState(data: HumanUIEntity) {
                    binding.textSuspendFromRepository.text = data.name
                    showMainContent()
                }

                override fun onErrorState(error: ApplicationError) {}

                override fun onLoadingState() = showProgressBar()

                override fun setupStartConfiguration() {
                    viewModel.loadRandomHuman()
                }
            }
        )
    }

    private val humanGeneratorHandler by inject<ViewHandler>(named(ViewHandlerEnum.HUMAN_GENERATOR)) {
        parametersOf(
            lifecycleScope,
            viewModel.humanFromGeneratorState,
            object : StateHandler.HandlerImplementation<HumanUIEntity> {
                override fun onSuccessState(data: HumanUIEntity) {
                    binding.textFlowFromRepository.text = data.name
                    showMainContent()
                }

                override fun onErrorState(error: ApplicationError) {}

                override fun onLoadingState() = showProgressBar()

                override fun setupStartConfiguration() {
                    viewModel.startGenerateHuman()
                }
            }
        )
    }


    private val navigationHandler by inject<ViewHandler>(named(ViewHandlerEnum.NAVIGATION)) {
        parametersOf(
            lifecycleScope,
            viewModel.navActionFlow,
            findNavController()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        randomHumanHandler.subscribe()
        humanGeneratorHandler.subscribe()
        navigationHandler.subscribe()
        binding.onSecondFragmentBtn.setOnClickListener { viewModel.onSecondFragmentBtnPressed() }
    }

    private fun hideAllContent() {
        binding.content.isVisible = false
        binding.progressBarLayout.content.isVisible = false
    }

    private fun showProgressBar() {
        hideAllContent()
        binding.progressBarLayout.content.isVisible = true
    }

    private fun showMainContent() {
        hideAllContent()
        binding.content.isVisible = true
    }
}