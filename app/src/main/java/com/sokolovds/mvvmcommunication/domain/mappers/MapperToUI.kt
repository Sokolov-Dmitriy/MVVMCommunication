package com.sokolovds.mvvmcommunication.domain.mappers

interface MapperToUI<UI> {
    fun toUIEntity(): UI
}