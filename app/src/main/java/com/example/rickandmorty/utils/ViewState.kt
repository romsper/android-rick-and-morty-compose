package com.example.rickandmorty.utils

sealed class ViewState<T> {
    class Idle<T>(val data: T?) : ViewState<T>()
    class Loading<T>(val data: T?) : ViewState<T>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error<T>(val message: String) : ViewState<T>()

    companion object {
        fun <T> idle(data: T?) = Idle(data)

        fun <T> loading(data: T?) = Loading(data)

        fun <T> success(data: T) = Success(data)

        fun <T> error(message: String) = Error<T>(message)
    }
}