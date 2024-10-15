package com.ar.moviesapp.domain.model

import kotlinx.serialization.*

@Serializable
data class Movie(
    private val adult: Boolean = true,
    private val backdrop_path: String = "",
    private val genre_ids: List<Int> = emptyList(),
    private val id: Int = 0,
    private val original_language: String = "",
    private val original_title: String = "",
    private val overview: String = "",
    private val popularity: Double = 0.0,
    private val poster_path: String = "",
    private val release_date: String = "",
    private val title: String = "",
    private val video: Boolean = true,
    private val vote_average: Double = 0.0,
    private val vote_count: Int = 0,
)
