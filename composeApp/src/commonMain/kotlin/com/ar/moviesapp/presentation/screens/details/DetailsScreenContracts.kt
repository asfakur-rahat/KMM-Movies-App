package com.ar.moviesapp.presentation.screens.details

import com.ar.moviesapp.data.remote.model.response.MovieCast
import com.ar.moviesapp.data.remote.model.response.MovieDetailsResponse
import com.ar.moviesapp.data.remote.model.response.MovieReview

data class DetailsScreenUiState(
    val movieId: Int = 0,
    val movieDetails: MovieDetailsResponse = MovieDetailsResponse(),
    val movieCast: List<MovieCast> = emptyList(),
    val movieReview: List<MovieReview> = emptyList()
)

sealed interface DetailsScreenEvent{
    data class SetMovieId(val movieId: Int): DetailsScreenEvent
    data class FetchMovieDetails(val movieId: Int): DetailsScreenEvent
}