package com.ar.moviesapp.presentation.screens.home

import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.base.MVIViewModel
import com.ar.moviesapp.core.networkUtils.onError
import com.ar.moviesapp.core.networkUtils.onSuccess
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.domain.repository.MovieRepository

class HomeViewModel(
    private val repository: MovieRepository,
) : MVIViewModel<BaseUiState<HomeScreenUiState>, HomeScreenEvent>() {

    private var _uiState = HomeScreenUiState()

    init {
        setState(BaseUiState.Data(_uiState))
    }

    override fun onTriggerEvent(eventType: HomeScreenEvent) {
        when (eventType) {
            HomeScreenEvent.FetchMovies -> fetchMovies()
            is HomeScreenEvent.OnSearchMode -> toogleSearchMode(eventType.isActive)
            is HomeScreenEvent.OnSearchQueryChange -> onSearchQueryChange(eventType.query)
            is HomeScreenEvent.OnSearch -> searchMovie(eventType.query)
        }
    }

    private fun toogleSearchMode(isActive: Boolean) = safeLaunch(disableStartLoading = true) {
        _uiState = _uiState.copy(
            searchMode = isActive
        )
        setState(BaseUiState.Data(_uiState))
    }

    private fun onSearchQueryChange(query: String) = safeLaunch(disableStartLoading = true) {
        _uiState = _uiState.copy(
            searchQuery = query
        )
        setState(BaseUiState.Data(_uiState))
    }

    private fun fetchMovies() = safeLaunch {
        repository.getTrendingMovies()
            .onSuccess {
                _uiState = _uiState.copy(
                    topFiveMovie = if (it.size > 5) it.take(5) else it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }


        repository.getTopRatedMovies(MovieRequest(language = "en-US", page = 1))
            .onSuccess {
                _uiState = _uiState.copy(
                    topRatedMovie = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }
        repository.getNowPlayingMovies(MovieRequest(language = "en-US", page = 1))
            .onSuccess {
                _uiState = _uiState.copy(
                    nowPlayingMovie = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }
        repository.getUpcomingMovies(MovieRequest(language = "en-US", page = 1))
            .onSuccess {
                _uiState = _uiState.copy(
                    upcomingMovie = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }

        repository.getPopularMovies(MovieRequest(language = "en-US", page = 1))
            .onSuccess {
                _uiState = _uiState.copy(
                    popularMovie = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }
    }

    private fun searchMovie(query: String) = safeLaunch(disableStartLoading = true) {
        repository.getMovieFromSearch(query)
            .onSuccess {
                _uiState = _uiState.copy(
                    searchResult = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }
    }
}