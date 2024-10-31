package com.ar.moviesapp.data.remote.model.response

import com.ar.moviesapp.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopRatedMovieResponse(
    var page: Int,
    var results: List<Movie>,
    @SerialName("total_pages") var totalPages: Int,
    @SerialName("total_results") var totalResults: Int
)
