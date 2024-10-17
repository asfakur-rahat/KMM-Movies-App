package com.ar.moviesapp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    var adult: Boolean = true,

    @SerialName("backdrop_path")
    var backdropPath: String? = null,

    @SerialName("genre_ids")
    var genreIds: List<Int> = emptyList(),

    var id: Int = 0,

    @SerialName("original_language")
    var originalLanguage: String = "",

    @SerialName("original_title")
    var originalTitle: String = "",

    var overview: String = "",
    var popularity: Double = 0.0,

    @SerialName("poster_path")
    var posterPath: String = "",

    @SerialName("release_date")
    var releaseDate: String = "",

    var title: String = "",
    var video: Boolean = true,

    @SerialName("vote_average")
    var voteAverage: Double = 0.0,

    @SerialName("vote_count")
    var voteCount: Int = 0,
)

@Serializable
data class TrendingMovie(
    var id: Int = 0,
    var title: String = "",
    @SerialName("poster_path")
    var posterPath: String = ""
)
