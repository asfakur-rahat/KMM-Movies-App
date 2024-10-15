package com.ar.moviesapp.domain.repository

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.domain.model.Movie

interface MovieRepository {
    suspend fun getNowPlayingMovies(request: MovieRequest): Result<List<Movie>, NetworkError>
    suspend fun getTopRatedMovies(request: MovieRequest): Result<List<Movie>, NetworkError>
}