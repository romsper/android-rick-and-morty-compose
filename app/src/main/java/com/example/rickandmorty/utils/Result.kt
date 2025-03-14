package com.example.rickandmorty.utils

@Deprecated("For Ktor only")
sealed class Result <out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val code: Int = 999, val message: String) : Result<T>()

    fun onSuccess(block: (T) -> Unit): Result<T> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    fun onError(block: (code: Int, msg: String) -> Unit): Result<T> {
        if (this is Error) {
            block(code, message)
        }
        return this
    }

    // Required for suspend Room database functions
    suspend fun onSuccessSuspend(block: suspend (T) -> Unit): Result<T> {
        if (this is Success) {
            block(data)
        }
        return this
    }

    // Required for suspend Room database functions
    suspend fun onErrorSuspend(block: suspend (code: Int, msg: String) -> Unit): Result<T> {
        if (this is Error) {
            block(code, message)
        }
        return this
    }
}