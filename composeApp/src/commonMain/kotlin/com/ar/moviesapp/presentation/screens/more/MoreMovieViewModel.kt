package com.ar.moviesapp.presentation.screens.more

import androidx.lifecycle.viewModelScope
import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.base.MVIViewModel
import com.ar.moviesapp.domain.repository.MovieRepository
import kotlinx.coroutines.launch

class MoreMovieViewModel(
    private val repository: MovieRepository,
): MVIViewModel<BaseUiState<MoreMovieScreenUiState>, MoreMovieScreenEvent>() {

    var _uiState = MoreMovieScreenUiState()

    init {
        setState(BaseUiState.Data(_uiState))
    }

    override fun onTriggerEvent(eventType: MoreMovieScreenEvent) {
        when(eventType){
            is MoreMovieScreenEvent.FetchMoreMovies -> getMoreMovies()
        }
    }

    private fun getMoreMovies() = viewModelScope.launch {
        val movies = repository.getMoreMovie()
        _uiState = _uiState.copy(movies = movies)
        setState(BaseUiState.Data(_uiState))
    }
}