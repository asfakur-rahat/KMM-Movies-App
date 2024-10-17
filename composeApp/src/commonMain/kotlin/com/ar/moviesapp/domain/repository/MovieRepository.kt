package com.ar.moviesapp.domain.repository

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.data.local.dto.MovieEntity
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.data.remote.model.response.MovieCast
import com.ar.moviesapp.data.remote.model.response.MovieDetailsResponse
import com.ar.moviesapp.data.remote.model.response.MovieReview
import com.ar.moviesapp.data.remote.model.response.SearchedMovie
import com.ar.moviesapp.domain.model.Movie
import com.ar.moviesapp.domain.model.TrendingMovie

interface MovieRepository {

    suspend fun getMovieFromSearch(query: String): Result<List<SearchedMovie>, NetworkError>
    suspend fun getTrendingMovies(): Result<List<TrendingMovie>, NetworkError>

    suspend fun getNowPlayingMovies(request: MovieRequest): Result<List<Movie>, NetworkError>
    suspend fun getUpcomingMovies(request: MovieRequest): Result<List<Movie>, NetworkError>
    suspend fun getTopRatedMovies(request: MovieRequest): Result<List<Movie>, NetworkError>
    suspend fun getPopularMovies(request: MovieRequest): Result<List<Movie>, NetworkError>

    suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsResponse, NetworkError>
    suspend fun getMovieCast(movieId: Int): Result<List<MovieCast>, NetworkError>
    suspend fun getMovieReview(movieId: Int): Result<List<MovieReview>, NetworkError>

    //Database Specific tasks
    suspend fun insertMovies(movies: List<MovieEntity>)
    suspend fun getMovieById(id: Int): MovieEntity?
    suspend fun getMovies(): List<MovieEntity>
    suspend fun clearMovies(id: Int)
}