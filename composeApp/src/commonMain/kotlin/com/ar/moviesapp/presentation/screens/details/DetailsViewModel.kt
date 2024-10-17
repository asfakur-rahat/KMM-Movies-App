package com.ar.moviesapp.presentation.screens.details

import com.ar.moviesapp.core.base.BaseUiState
import com.ar.moviesapp.core.base.MVIViewModel
import com.ar.moviesapp.core.networkUtils.onError
import com.ar.moviesapp.core.networkUtils.onSuccess
import com.ar.moviesapp.core.utils.toGenre
import com.ar.moviesapp.data.local.dto.MovieEntity
import com.ar.moviesapp.domain.repository.MovieRepository

class DetailsViewModel(
    private val repository: MovieRepository
): MVIViewModel<BaseUiState<DetailsScreenUiState>, DetailsScreenEvent>() {

    private var _uiState = DetailsScreenUiState()

    init {
        setState(BaseUiState.Data(_uiState))
    }

    override fun onTriggerEvent(eventType: DetailsScreenEvent) {
        when(eventType){
            is DetailsScreenEvent.FetchMovieDetails -> fetchMovieDetails(eventType.movieId)
            is DetailsScreenEvent.SetMovieId -> {
                _uiState = _uiState.copy(
                    movieId = eventType.movieId
                )
                setState(BaseUiState.Data(_uiState))
            }
            is DetailsScreenEvent.AddToWatchList -> addToWatchList(eventType.movieId)
            DetailsScreenEvent.RemoveFromWatchList -> removeFromWatchList(_uiState.movieId)
        }
    }

    private fun fetchMovieDetails(movieId: Int) = safeLaunch {
        val movie = repository.getMovieById(movieId)
        movie?.let {
            _uiState = _uiState.copy(
                isBookMarked = true
            )
            setState(BaseUiState.Data(_uiState))
        }
        repository.getMovieDetails(movieId)
            .onSuccess {
                _uiState = _uiState.copy(
                    movieDetails = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }
        repository.getMovieCast(movieId)
            .onSuccess {
                println(it)
                _uiState = _uiState.copy(
                    movieCast = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }

        repository.getMovieReview(movieId)
            .onSuccess {
                _uiState = _uiState.copy(
                    movieReview = it
                )
                setState(BaseUiState.Data(_uiState))
            }
            .onError {
                setState(BaseUiState.Error(Throwable(message = it.name)))
            }
    }
    private fun addToWatchList(movieId: Int) = safeLaunch {
        repository.insertMovies(
            listOf(
                MovieEntity(
                    id = movieId,
                    title = _uiState.movieDetails.title,
                    posterPath = _uiState.movieDetails.posterPath,
                    overview = _uiState.movieDetails.overview,
                    releaseDate = _uiState.movieDetails.releaseDate,
                    voteAverage = _uiState.movieDetails.voteAverage,
                    genre = _uiState.movieDetails.genres.toGenre(),
                    runtime = _uiState.movieDetails.runtime
                )
            )
        )
        _uiState = _uiState.copy(
            isBookMarked = true
        )
        setState(BaseUiState.Data(_uiState))
    }
    private fun removeFromWatchList(movieId: Int) = safeLaunch {
        repository.clearMovies(movieId)
        _uiState = _uiState.copy(
            isBookMarked = false
        )
        setState(BaseUiState.Data(_uiState))
    }
}