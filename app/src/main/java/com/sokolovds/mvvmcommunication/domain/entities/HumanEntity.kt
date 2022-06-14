package com.sokolovds.mvvmcommunication.domain.entities

import com.sokolovds.mvvmcommunication.data.entities.HumanDataEntity
import com.sokolovds.mvvmcommunication.domain.mappers.MapperToData
import com.sokolovds.mvvmcommunication.domain.mappers.MapperToUI
import com.sokolovds.mvvmcommunication.presentation.entities.HumanUIEntity

data class HumanEntity(
    val name: String,
    val age: Int
) : MapperToUI<HumanUIEntity>, MapperToData<HumanDataEntity> {
    override fun toDataEntity(): HumanDataEntity =
        HumanDataEntity(
            name = name,
            age = age
        )

    override fun toUIEntity(): HumanUIEntity =
        HumanUIEntity(
            name = name,
            age = age
        )
}