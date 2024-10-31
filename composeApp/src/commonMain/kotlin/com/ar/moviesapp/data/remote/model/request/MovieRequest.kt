package com.ar.moviesapp.data.remote.model.request

import kotlinx.serialization.Serializable

@Serializable
data class MovieRequest(
    val language: String = "en-US",
    val page: Int = 1,
)
