package com.sokolovds.mvvmcommunication.domain.mappers

interface MapperToDomain<DOMAIN> {
    fun toDomainEntity(): DOMAIN
}