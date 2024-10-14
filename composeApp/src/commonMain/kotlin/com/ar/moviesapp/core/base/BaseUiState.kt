package com.ar.moviesapp.core.base

sealed interface BaseUiState<out T>{
    data object Loading: BaseUiState<Nothing>
    data object Empty: BaseUiState<Nothing>
    data class Data<T>(val data: T): BaseUiState<T>
    data class Error(val error: Throwable): BaseUiState<Nothing>
}