package com.ar.moviesapp.data.remote.model.response

import com.ar.moviesapp.domain.model.Movie
import kotlinx.serialization.*

@Serializable
data class NowPlayingMovieResponse(
    val dates: Dates,
    val page: Int = 0,
    val results: List<Movie> = emptyList(),
    val total_pages: Int = 0,
    val total_results: Int = 0,
)

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String,
)