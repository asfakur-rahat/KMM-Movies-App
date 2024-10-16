package com.ar.moviesapp.data.remote.api

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.data.remote.model.response.MovieCastResponse
import com.ar.moviesapp.data.remote.model.response.MovieDetailsResponse
import com.ar.moviesapp.data.remote.model.response.MovieReviewResponse
import com.ar.moviesapp.data.remote.model.response.NowPlayingMovieResponse
import com.ar.moviesapp.data.remote.model.response.PopularMovieResponse
import com.ar.moviesapp.data.remote.model.response.TopRatedMovieResponse
import com.ar.moviesapp.data.remote.model.response.UpcomingMovieResponse

interface MovieApi {
    suspend fun getNowPlayingMovies(request: MovieRequest): Result<NowPlayingMovieResponse, NetworkError>
    suspend fun getUpcomingMovies(request: MovieRequest): Result<UpcomingMovieResponse, NetworkError>
    suspend fun getTopRatedMovies(request: MovieRequest): Result<TopRatedMovieResponse, NetworkError>
    suspend fun getPopularMovies(request: MovieRequest): Result<PopularMovieResponse, NetworkError>

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsResponse, NetworkError>
    suspend fun getMovieCast(movieId: Int): Result<MovieCastResponse, NetworkError>
    suspend fun getMovieReview(movieId: Int): Result<MovieReviewResponse, NetworkError>
}