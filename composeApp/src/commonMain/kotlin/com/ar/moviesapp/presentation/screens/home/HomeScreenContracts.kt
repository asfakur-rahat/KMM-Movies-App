package com.ar.moviesapp.presentation.screens.home

import com.ar.moviesapp.domain.model.Movie

data class HomeScreenUiState(
    val topFiveMovie: List<Movie> = emptyList(),
    val topRatedMovie: List<Movie> = emptyList(),
    val nowPlayingMovie: List<Movie> = emptyList(),
    val popularMovie: List<Movie> = emptyList(),
    val upcomingMovie: List<Movie> = emptyList(),
)

sealed interface HomeScreenEvent {
    data class OnClickMovie(val movie: Movie) : HomeScreenEvent
    data object FetchMovies : HomeScreenEvent
}