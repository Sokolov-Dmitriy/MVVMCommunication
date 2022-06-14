package com.sokolovds.mvvmcommunication.presentation.secondScreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sokolovds.mvvmcommunication.R
import com.sokolovds.mvvmcommunication.databinding.SecondFragmentBinding
import com.sokolovds.mvvmcommunication.di.ViewHandlerEnum
import com.sokolovds.mvvmcommunication.presentation.utils.ViewHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class SecondFragment : Fragment(R.layout.second_fragment) {
    private val binding by viewBinding(SecondFragmentBinding::bind)
    private val viewModel by viewModel<SecondFragmentViewModel>()
    private val navigationHandler by inject<ViewHandler>(named(ViewHandlerEnum.NAVIGATION)) {
        parametersOf(
            lifecycleScope,
            viewModel.navActionFlow,
            findNavController()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigationHandler.subscribe()
        binding.backBtn.setOnClickListener {
            viewModel.onBackPressed()
        }
    }
}