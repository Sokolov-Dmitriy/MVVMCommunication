package com.sokolovds.mvvmcommunication.presentation.entities

import com.sokolovds.mvvmcommunication.domain.mappers.MapperToDomain
import com.sokolovds.mvvmcommunication.domain.entities.HumanEntity


data class HumanUIEntity(
    val name: String,
    val age: Int
) : MapperToDomain<HumanEntity> {
    override fun toDomainEntity(): HumanEntity =
        HumanEntity(
            name = name,
            age = age
        )
}
