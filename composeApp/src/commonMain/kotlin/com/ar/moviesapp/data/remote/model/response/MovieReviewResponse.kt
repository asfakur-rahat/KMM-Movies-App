package com.ar.moviesapp.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewResponse(
    var id: Int = 0,
    var page: Int = 0,
    var results: List<MovieReview> = emptyList()
)

@Serializable
data class MovieReview(
    var author: String = "",
    @SerialName("author_details") var authorDetails: AuthorDetails = AuthorDetails(),
    var content: String = "",
)

@Serializable
data class AuthorDetails(
    @SerialName("avatar_path") var avatarPath: String? = null,
    var rating: Double? = null,
)
