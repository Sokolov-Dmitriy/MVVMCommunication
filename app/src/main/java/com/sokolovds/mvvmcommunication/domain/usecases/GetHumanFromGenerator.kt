package com.sokolovds.mvvmcommunication.domain.usecases

import com.sokolovds.mvvmcommunication.domain.HumanRepository
import com.sokolovds.mvvmcommunication.domain.UseCase
import com.sokolovds.mvvmcommunication.domain.entities.HumanEntity
import com.sokolovds.mvvmcommunication.presentation.entities.HumanUIEntity

class GetHumanFromGenerator(private val repository: HumanRepository) : UseCase<HumanEntity,HumanUIEntity> {
    operator fun invoke() = repository.getHumanGenerator()
}