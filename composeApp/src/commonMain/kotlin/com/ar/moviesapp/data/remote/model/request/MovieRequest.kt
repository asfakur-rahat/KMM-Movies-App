package com.ar.moviesapp.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class MovieRequest(
    val language: String = "en-US",
    val page: Int = 1,
    val region: String = "US",
    val sort_by: String = "popularity.desc",
    val include_adult: Boolean = false,
    val include_video: Boolean = false,
)
