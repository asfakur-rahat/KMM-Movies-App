package com.ar.moviesapp.presentation.screens.search

import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.base.MVIViewModel
import com.ar.moviesapp.core.networkUtils.onError
import com.ar.moviesapp.core.networkUtils.onSuccess
import com.ar.moviesapp.domain.repository.MovieRepository

class SearchViewModel(
    private val repository: MovieRepository
):MVIViewModel<BaseUiState<SearchScreenUiState>, SearchScreenEvent>() {

    private var _uiState = SearchScreenUiState()

    init {
        setState(BaseUiState.Data(_uiState))
    }

    override fun onTriggerEvent(eventType: SearchScreenEvent) {
        when(eventType){
            is SearchScreenEvent.OnSearch -> getMovieFromSearch(eventType.query)
            is SearchScreenEvent.OnSearchMode -> setSearchMode(eventType.isActive)
            is SearchScreenEvent.OnSearchQueryChange -> setSearchQuery(eventType.query)
        }
    }

    private fun getMovieFromSearch(query: String) = safeLaunch {
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
    private fun setSearchQuery(query: String) {
        _uiState = _uiState.copy(searchQuery = query)
        setState(BaseUiState.Data(_uiState))
    }
    private fun setSearchMode(isActive: Boolean) {
        _uiState = _uiState.copy(searchMode = isActive)
        setState(BaseUiState.Data(_uiState))
    }
}