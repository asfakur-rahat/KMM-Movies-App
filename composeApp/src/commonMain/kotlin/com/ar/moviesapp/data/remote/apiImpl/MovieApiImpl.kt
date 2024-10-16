package com.ar.moviesapp.data.remote.apiImpl

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.core.networkUtils.callApiService
import com.ar.moviesapp.data.remote.api.MovieApi
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.data.remote.model.response.MovieCastResponse
import com.ar.moviesapp.data.remote.model.response.MovieDetailsResponse
import com.ar.moviesapp.data.remote.model.response.MovieReviewResponse
import com.ar.moviesapp.data.remote.model.response.NowPlayingMovieResponse
import com.ar.moviesapp.data.remote.model.response.PopularMovieResponse
import com.ar.moviesapp.data.remote.model.response.TopRatedMovieResponse
import com.ar.moviesapp.data.remote.model.response.UpcomingMovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieApiImpl(
    private val client: HttpClient,
): MovieApi {
    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3"
        private const val API_KEY = "d769d990b72bac3230103f9f87450ea3"
    }

    override suspend fun getNowPlayingMovies(request: MovieRequest): Result<NowPlayingMovieResponse, NetworkError> {
        callApiService<NowPlayingMovieResponse>(
            api = {
                client.get("${BASE_URL}/movie/now_playing"){
                    parameter("language", request.language)
                    parameter("page", request.page)
                    parameter("api_key", API_KEY)
                }
            },
            onSuccess = {
                return Result.Success(it)
            },
            onError = {
                return Result.Error(it)
            }
        )
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getUpcomingMovies(request: MovieRequest): Result<UpcomingMovieResponse, NetworkError> {
        callApiService<UpcomingMovieResponse>(
            api = {
                client.get("${BASE_URL}/movie/upcoming"){
                    parameter("language", request.language)
                    parameter("page", request.page)
                    parameter("api_key", API_KEY)
                }
            },
            onSuccess = {
                return Result.Success(it)
            },
            onError = {
                return Result.Error(it)
            }
        )
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getTopRatedMovies(request: MovieRequest): Result<TopRatedMovieResponse, NetworkError> {
        callApiService<TopRatedMovieResponse>(
            api = {
                client.get("${BASE_URL}/movie/top_rated"){
                    parameter("language", request.language)
                    parameter("page", request.page)
                    parameter("api_key", API_KEY)
                }
            },
            onSuccess = {
                return Result.Success(it)
            },
            onError = {
                return Result.Error(it)
            }
        )
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getPopularMovies(request: MovieRequest): Result<PopularMovieResponse, NetworkError> {
        callApiService<PopularMovieResponse>(
            api = {
                client.get("${BASE_URL}/movie/popular"){
                    parameter("language", request.language)
                    parameter("page", request.page)
                    parameter("api_key", API_KEY)
                }
            },
            onSuccess = {
                return Result.Success(it)
            },
            onError = {
                return Result.Error(it)
            }
        )
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetailsResponse, NetworkError> {
        println("apiImpl")
        callApiService<MovieDetailsResponse>(
            api = {
                client.get("${BASE_URL}/movie/$movieId"){
                    parameter("language", "en-US")
                    parameter("api_key", API_KEY)
                }
            },
            onSuccess = {
                return Result.Success(it)
            },
            onError = {
                return Result.Error(it)
            }
        )
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getMovieCast(movieId: Int): Result<MovieCastResponse, NetworkError> {
        callApiService<MovieCastResponse>(
            api = {
                client.get("${BASE_URL}/movie/$movieId/credits"){
                    parameter("language", "en-US")
                    parameter("api_key", API_KEY)
                }
            },
            onSuccess = {
                println(it)
                return Result.Success(it)
            },
            onError = {
                return Result.Error(it)
            }
        )
        return Result.Error(NetworkError.UNKNOWN)
    }

    override suspend fun getMovieReview(movieId: Int): Result<MovieReviewResponse, NetworkError> {
        callApiService<MovieReviewResponse>(
            api = {
                client.get("${BASE_URL}/movie/$movieId/reviews"){
                    parameter("language", "en-US")
                    parameter("page", 1)
                    parameter("api_key", API_KEY)
                }
            },
            onSuccess = {
                println(it)
                return Result.Success(it)
            },
            onError = {
                return Result.Error(it)
            }
        )
        return Result.Error(NetworkError.UNKNOWN)
    }
}