package com.ar.moviesapp.data.remote.model.response

import com.ar.moviesapp.domain.model.TrendingMovie
import kotlinx.serialization.Serializable

@Serializable
data class TrendingMovieResponse(
    var page: Int = 0,
    var results: List<TrendingMovie> = emptyList(),
)
