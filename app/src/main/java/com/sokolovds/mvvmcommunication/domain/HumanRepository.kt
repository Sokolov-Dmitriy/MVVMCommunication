package com.sokolovds.mvvmcommunication.domain

import com.sokolovds.mvvmcommunication.domain.entities.HumanEntity
import kotlinx.coroutines.flow.Flow

interface HumanRepository {
    suspend fun getRandomHuman(): Result<HumanEntity>
    fun getHumanGenerator(): Flow<Result<HumanEntity>>
}