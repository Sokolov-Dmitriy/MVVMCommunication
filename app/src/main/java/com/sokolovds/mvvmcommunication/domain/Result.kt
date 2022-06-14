package com.sokolovds.mvvmcommunication.domain

sealed class Result<out T> {

    data class Success<out R>(
        val value: R
    ) : Result<R>()

    data class Error(
        val exception: ApplicationError
    ) : Result<Nothing>()

    object Loading : Result<Nothing>()
}


inline fun <reified T> Result<T>.onError(block: (exception: ApplicationError) -> Unit): Result<T> {
    if (this is Result.Error) {
        block(exception)
    }
    return this
}

inline fun <reified T> Result<T>.onSuccess(block: (value: T) -> Unit): Result<T> {
    if (this is Result.Success) {
        block(value)
    }
    return this
}

inline fun <reified T> Result<T>.onLoading(block: () -> Unit): Result<T> {
    if (this is Result.Loading) {
        block()
    }
    return this
}

sealed class ApplicationError
