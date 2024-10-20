package com.ar.moviesapp.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieCastResponse(
    var id: Int = 0,
    var cast: List<MovieCast> = emptyList()
)

@Serializable
data class MovieCast(
    var id: Int = 0,
    var name: String = "",
    @SerialName("profile_path") var profilePath: String? = null
)
