package com.sokolovds.mvvmcommunication.domain.mappers

interface MapperToData<DATA> {
    fun toDataEntity(): DATA
}