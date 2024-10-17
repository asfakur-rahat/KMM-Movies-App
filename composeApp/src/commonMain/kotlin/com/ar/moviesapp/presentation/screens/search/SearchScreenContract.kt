package com.ar.moviesapp.presentation.screens.search

import com.ar.moviesapp.data.remote.model.response.SearchedMovie

data class SearchScreenUiState(
    val searchQuery: String = "",
    val searchMode: Boolean = false,
    val searchResult: List<SearchedMovie> = emptyList(),
)