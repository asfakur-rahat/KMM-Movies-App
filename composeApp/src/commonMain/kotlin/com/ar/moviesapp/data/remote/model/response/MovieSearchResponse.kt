package com.ar.moviesapp.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieSearchResponse(
    val results: List<SearchedMovie> = emptyList(),
)

@Serializable
data class SearchedMovie(
    val id: Int = 0,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    val title: String = "",
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("release_date")
    val releaseDate: String = "",
)