package com.ar.moviesapp.data.remote.apiImpl

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.core.networkUtils.callApiService
import com.ar.moviesapp.data.remote.api.MovieApi
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.data.remote.model.response.NowPlayingMovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class MovieApiImpl(
    private val client: HttpClient,
): MovieApi {
    companion object{
        private const val BASE_URL = "https://api.themoviedb.org/3/"
    }

    override suspend fun getNowPlayingMovies(request: MovieRequest): Result<NowPlayingMovieResponse, NetworkError> {
        callApiService<NowPlayingMovieResponse>(
            api = {
                client.get("${BASE_URL}movie/now_playing"){
                    parameter("language", request.language)
                    parameter("page", request.page)
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
}