package com.ar.moviesapp.domain.repository

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.data.remote.model.response.MovieCast
import com.ar.moviesapp.data.remote.model.response.MovieDetailsResponse
import com.ar.moviesapp.data.remote.model.response.MovieReview
import com.ar.moviesapp.domain.model.Movie

interface MovieRepository {
    suspend fun getNowPlayingMovies(request: MovieRequest): Result<List<Movie>, NetworkError>
    suspend fun getUpcomingMovies(request: MovieRequest): Result<List<Movie>, NetworkError>
    suspend fun getTopRatedMovies(request: MovieRequest): Result<List<Movie>, NetworkError>
    suspend fun getPopularMovies(request: MovieRequest): Result<List<Movie>, NetworkError>

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsResponse, NetworkError>
    suspend fun getMovieCast(movieId: Int): Result<List<MovieCast>, NetworkError>
    suspend fun getMovieReview(movieId: Int): Result<List<MovieReview>, NetworkError>
}