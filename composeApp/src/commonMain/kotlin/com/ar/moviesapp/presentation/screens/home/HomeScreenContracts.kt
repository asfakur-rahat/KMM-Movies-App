package com.ar.moviesapp.presentation.screens.home

import com.ar.moviesapp.data.remote.model.response.SearchedMovie
import com.ar.moviesapp.domain.model.Movie


data class HomeScreenUiState(
    val searchMode: Boolean = false,
    val searchQuery: String = "",
    val searchResult: List<SearchedMovie> = emptyList(),
    val topFiveMovie: List<Movie> = emptyList(),
    val topRatedMovie: List<Movie> = emptyList(),
    val nowPlayingMovie: List<Movie> = emptyList(),
    val popularMovie: List<Movie> = emptyList(),
    val upcomingMovie: List<Movie> = emptyList(),
)

sealed interface HomeScreenEvent {
    data object FetchMovies : HomeScreenEvent
    data class OnSearchMode(val isActive: Boolean) : HomeScreenEvent
    data class OnSearchQueryChange(val query: String) : HomeScreenEvent
    data class OnSearch(val query: String) : HomeScreenEvent
}