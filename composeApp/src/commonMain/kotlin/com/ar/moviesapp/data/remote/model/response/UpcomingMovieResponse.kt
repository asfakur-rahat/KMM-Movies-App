package com.ar.moviesapp.data.remote.model.response

import com.ar.moviesapp.domain.model.Movie
import kotlinx.serialization.Serializable

@Serializable
data class UpcomingMovieResponse(
    val dates: Dates,
    val page: Int = 0,
    val results: List<Movie> = emptyList(),
    val total_pages: Int = 0,
    val total_results: Int = 0,
)