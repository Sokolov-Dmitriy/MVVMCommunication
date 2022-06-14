package com.sokolovds.mvvmcommunication.data

import com.github.javafaker.Faker
import com.sokolovds.mvvmcommunication.domain.HumanRepository
import com.sokolovds.mvvmcommunication.domain.Result
import com.sokolovds.mvvmcommunication.domain.entities.HumanEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

class HumanRepositoryImpl(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : HumanRepository {

    private val faker = Faker()

    override suspend fun getRandomHuman() = withContext(dispatcher) {
        delay(2000)
        return@withContext Result.Success(
            HumanEntity(
                name = faker.name().firstName(),
                age = faker.number().numberBetween(18, 40)
            )
        )
    }

    override fun getHumanGenerator(): Flow<Result<HumanEntity>> =
        flow<Result<HumanEntity>> {
            while (true) {
                delay(2000)
                emit(
                    Result.Success(
                        HumanEntity(
                            name = faker.name().firstName(),
                            age = faker.number().numberBetween(18, 40)
                        )
                    )
                )
            }
        }
            .onStart { emit(Result.Loading) }
            .flowOn(dispatcher)

}