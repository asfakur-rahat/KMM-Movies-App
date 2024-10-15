package com.ar.moviesapp.presentation.screens.home

import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.base.MVIViewModel
import com.ar.moviesapp.core.networkUtils.onError
import com.ar.moviesapp.core.networkUtils.onSuccess
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.domain.repository.MovieRepository

class HomeViewModel(
    private val repository: MovieRepository
): MVIViewModel<BaseUiState<HomeScreenUiState>, HomeScreenEvent>() {

    private var _uiState = HomeScreenUiState()

    init {
        setState(BaseUiState.Data(_uiState))
    }

    override fun onTriggerEvent(eventType: HomeScreenEvent) {
        when(eventType){
            is HomeScreenEvent.OnClickMovie -> {

            }
            HomeScreenEvent.FetchMovies -> fetchMovies()
        }
    }

    private fun fetchMovies() = safeLaunch {
        println("Here")
        repository.getTopRatedMovies(MovieRequest(language = "en-US", page = 1))
            .onSuccess {
                _uiState = _uiState.copy(
                    topFiveMovie = it.take(5),
                    topRatedMovie = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }
    }
}