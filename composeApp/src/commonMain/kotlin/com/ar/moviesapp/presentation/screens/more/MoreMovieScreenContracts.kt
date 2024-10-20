package com.ar.moviesapp.presentation.screens.more

import app.cash.paging.PagingData
import com.ar.moviesapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class MoreMovieScreenUiState(
    val movies: Flow<PagingData<Movie>> = emptyFlow()
)

sealed interface MoreMovieScreenEvent {
    data class FetchMoreMovies(val castId: Int) : MoreMovieScreenEvent
}