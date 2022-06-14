package com.sokolovds.mvvmcommunication.di

import com.sokolovds.mvvmcommunication.data.HumanRepositoryImpl
import com.sokolovds.mvvmcommunication.domain.HumanRepository
import com.sokolovds.mvvmcommunication.domain.usecases.GetHumanFromGenerator
import com.sokolovds.mvvmcommunication.domain.usecases.GetRandomHuman
import com.sokolovds.mvvmcommunication.presentation.entities.HumanUIEntity
import com.sokolovds.mvvmcommunication.presentation.firstScreen.FirstFragmentViewModel
import com.sokolovds.mvvmcommunication.presentation.secondScreen.SecondFragmentViewModel
import com.sokolovds.mvvmcommunication.presentation.utils.*
import com.sokolovds.mvvmcommunication.presentation.utils.navigationHandler.NavigationController
import com.sokolovds.mvvmcommunication.presentation.utils.navigationHandler.NavigationHandler
import com.sokolovds.mvvmcommunication.presentation.utils.stateHandler.StateHandler
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {

    single<HumanRepository> {
        HumanRepositoryImpl()
    }
}

val domainModule = module {

    factory<GetRandomHuman> {
        GetRandomHuman(
            repository = get()
        )
    }

    factory<GetHumanFromGenerator> {
        GetHumanFromGenerator(
            repository = get()
        )
    }
}

val presentationModule = module {

    factory<ViewHandler>(named(ViewHandlerEnum.RANDOM_HUMAN)) { args ->
        StateHandler.Base<HumanUIEntity>(
            lifecycleScope = args.get(),
            stateFlow = args.get(),
            handler = args.get()
        )
    }

    factory<ViewHandler>(named(ViewHandlerEnum.HUMAN_GENERATOR)) { args ->
        StateHandler.Base<HumanUIEntity>(
            lifecycleScope = args.get(),
            stateFlow = args.get(),
            handler = args.get()
        )
    }

    factory<ViewHandler>(named(ViewHandlerEnum.NAVIGATION)) { args ->
        NavigationHandler.Base(
            lifecycleScope = args.get(),
            navActionFlow = args.get(),
            navController = args.get()
        )
    }

    factory<NavigationController> {
        NavigationController.NavigationControllerImpl()
    }

    viewModel<FirstFragmentViewModel> {
        FirstFragmentViewModel(
            navigationController = get(),
            getHumanFromGenerator = get(),
            getRandomHuman = get()
        )
    }

    viewModel<SecondFragmentViewModel> {
        SecondFragmentViewModel(
            navigationController = get()
        )
    }
}