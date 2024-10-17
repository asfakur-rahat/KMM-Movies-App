package com.ar.moviesapp.presentation.screens.watchlist

import com.ar.moviesapp.data.local.dto.MovieEntity

data class WatchlistScreenUiState(
    val watchList: List<MovieEntity> = emptyList(),
)

sealed interface WatchlistScreenEvent {
    data object FetchWatchList : WatchlistScreenEvent
}