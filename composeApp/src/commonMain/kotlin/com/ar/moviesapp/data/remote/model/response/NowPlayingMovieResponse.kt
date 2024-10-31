package com.ar.moviesapp.data.remote.model.response

import com.ar.moviesapp.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NowPlayingMovieResponse(
    val dates: Dates,
    val page: Int = 0,
    val results: List<Movie> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0,
)

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String,
)