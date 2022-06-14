package com.sokolovds.mvvmcommunication.data.entities

import com.sokolovds.mvvmcommunication.domain.entities.HumanEntity
import com.sokolovds.mvvmcommunication.domain.mappers.MapperToDomain

data class HumanDataEntity(
    val name: String,
    val age: Int
) : MapperToDomain<HumanEntity> {
    override fun toDomainEntity(): HumanEntity =
        HumanEntity(
            name = name,
            age = age
        )
}
