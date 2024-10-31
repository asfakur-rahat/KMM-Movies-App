package com.ar.moviesapp.presentation.screens.search

import com.ar.moviesapp.data.remote.model.response.SearchedMovie

data class SearchScreenUiState(
    val searchQuery: String = "",
    val searchMode: Boolean = false,
    val searchResult: List<SearchedMovie> = emptyList(),
)

sealed interface SearchScreenEvent {
    data class OnSearch(val query: String): SearchScreenEvent
    data class OnSearchMode(val isActive: Boolean): SearchScreenEvent
    data class OnSearchQueryChange(val query: String): SearchScreenEvent
}