package com.ar.moviesapp.presentation.screens.watchlist

import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.base.MVIViewModel
import com.ar.moviesapp.domain.repository.MovieRepository

class WatchlistViewModel(
    private val repository: MovieRepository
):MVIViewModel<BaseUiState<WatchlistScreenUiState>, WatchlistScreenEvent>() {
    private var _uiState = WatchlistScreenUiState()

    init {
        setState(BaseUiState.Data(_uiState))
    }

    override fun onTriggerEvent(eventType: WatchlistScreenEvent) {
        when(eventType){
            WatchlistScreenEvent.FetchWatchList -> fetchWatchList()
        }
    }

    private fun fetchWatchList() = safeLaunch{
        val watchList = repository.getMovies()
        _uiState = _uiState.copy(
            watchList = watchList
        )
        setState(BaseUiState.Data(_uiState))
    }
}