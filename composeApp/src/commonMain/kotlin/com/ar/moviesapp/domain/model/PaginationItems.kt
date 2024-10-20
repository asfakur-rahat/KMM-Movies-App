package com.ar.moviesapp.domain.model

data class PaginationItems<T>(
    val items: List<T>,
    val page: Int,
    val total: Long
)
