package com.ar.moviesapp.data.repositoryImpl

import androidx.paging.PagingConfig
import app.cash.paging.Pager
import app.cash.paging.PagingData
import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.core.networkUtils.onError
import com.ar.moviesapp.core.networkUtils.onSuccess
import com.ar.moviesapp.data.local.db.AppDataBase
import com.ar.moviesapp.data.local.dto.MovieEntity
import com.ar.moviesapp.data.remote.api.MovieApi
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.data.remote.model.response.MovieCast
import com.ar.moviesapp.data.remote.model.response.MovieDetailsResponse
import com.ar.moviesapp.data.remote.model.response.MovieReview
import com.ar.moviesapp.data.remote.model.response.SearchedMovie
import com.ar.moviesapp.data.remote.pageSource.MoviePagingSource
import com.ar.moviesapp.domain.model.Movie
import com.ar.moviesapp.domain.model.TrendingMovie
import com.ar.moviesapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val api: MovieApi,
    private val db: AppDataBase,
    private val pagingSource: MoviePagingSource
): MovieRepository {
    override suspend fun getMoreMovie(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = {
                pagingSource
            }
        ).flow
    }
    override suspend fun getMovieFromSearch(query: String): Result<List<SearchedMovie>, NetworkError> {
        api.getMovieFromSearch(query)
            .onSuccess {
                val result = it.results.filter { movie ->
                    movie.posterPath != null && movie.backdropPath != null
                }
                return Result.Success(result)
            }
            .onError {
                Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getTrendingMovies(): Result<List<TrendingMovie>, NetworkError> {
        api.getTrendingMovies()
            .onSuccess {
                return Result.Success(it.results)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

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
                    review.authorDetails.rating != null
                }
                return Result.Success(result)
            }
            .onError {
                return Result.Error(it)
            }
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        db.movieDao().insertMovies(movies)
    }

    override suspend fun getMovieById(id: Int): MovieEntity? {
        return db.movieDao().getMovieById(id)
    }

    override suspend fun getMovies(): List<MovieEntity> {
        return db.movieDao().getMovies()
    }

    override suspend fun clearMovies(id: Int) {
        db.movieDao().clearMovies(id)
    }
}