package com.ar.moviesapp.data.remote.pageSource

import com.ar.moviesapp.core.base.BasePagingSource
import com.ar.moviesapp.data.remote.api.MovieApi
import com.ar.moviesapp.domain.model.Movie
import com.ar.moviesapp.domain.model.PaginationItems

class MoviePagingSource(
    private val api: MovieApi,
) : BasePagingSource<Movie>() {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Movie> {
        return api.getMoreMovies(page, limit)
    }
}