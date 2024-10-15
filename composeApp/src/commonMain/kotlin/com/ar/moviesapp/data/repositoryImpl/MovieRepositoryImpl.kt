package com.ar.moviesapp.data.repositoryImpl

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.core.networkUtils.onError
import com.ar.moviesapp.core.networkUtils.onSuccess
import com.ar.moviesapp.data.remote.api.MovieApi
import com.ar.moviesapp.data.remote.model.request.MovieRequest
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
}