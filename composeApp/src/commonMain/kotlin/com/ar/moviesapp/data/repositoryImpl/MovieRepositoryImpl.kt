package com.ar.moviesapp.data.repositoryImpl

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.core.networkUtils.onError
import com.ar.moviesapp.core.networkUtils.onSuccess
import com.ar.moviesapp.core.utils.cast
import com.ar.moviesapp.data.remote.api.MovieApi
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.data.remote.model.response.MovieCast
import com.ar.moviesapp.data.remote.model.response.MovieDetailsResponse
import com.ar.moviesapp.data.remote.model.response.MovieReview
import com.ar.moviesapp.domain.model.Movie
import com.ar.moviesapp.domain.repository.MovieRepository

class MovieRepositoryImpl(
    private val api: MovieApi
): MovieRepository {
    override suspend fun getNowPlayingMovies(request: MovieRequest): Result<List<Movie>, NetworkError> {
        api.getNowPlayingMovies(request)
            .onSuccess {
                return Result.Success(it.results)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getUpcomingMovies(request: MovieRequest): Result<List<Movie>, NetworkError> {
        api.getUpcomingMovies(request)
            .onSuccess {
                return Result.Success(it.results)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getTopRatedMovies(request: MovieRequest): Result<List<Movie>, NetworkError> {
        api.getTopRatedMovies(request)
            .onSuccess {
                return Result.Success(it.results)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getPopularMovies(request: MovieRequest): Result<List<Movie>, NetworkError> {
        api.getPopularMovies(request)
            .onSuccess {
                return Result.Success(it.results)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsResponse, NetworkError> {
        api.getMovieDetails(movieId)
            .onSuccess {
                println(it)
                return Result.Success(it)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getMovieCast(movieId: Int): Result<List<MovieCast>, NetworkError> {
        api.getMovieCast(movieId)
            .onSuccess {
                println(it)
                return Result.Success(it.cast)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getMovieReview(movieId: Int): Result<List<MovieReview>, NetworkError> {
        api.getMovieReview(movieId)
            .onSuccess {
                val result = it.results.filter { review ->
                    review.authorDetails.avatarPath != null && review.authorDetails.rating != null
                }
                return Result.Success(result)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }
}