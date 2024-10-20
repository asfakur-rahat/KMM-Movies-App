package com.ar.moviesapp.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    var adult: Boolean = true,
    @SerialName("backdrop_path") var backdropPath: String? = null,
    var budget: Int = 0,
    var genres: List<Genre> = emptyList(),
    var id: Int = 0,
    var overview: String = "",
    var runtime: Int = 0,
    @SerialName("poster_path") var posterPath: String? = null,
    var title: String = "",
    @SerialName("release_date") var releaseDate: String = "",
    @SerialName("vote_average") var voteAverage: Double = 0.0,
)

@Serializable
data class Genre(
    var id: Int = 0,
    var name: String? = null,
)