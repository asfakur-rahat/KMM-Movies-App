package com.ar.moviesapp.data.remote.api

import com.ar.moviesapp.core.networkUtils.NetworkError
import com.ar.moviesapp.core.networkUtils.Result
import com.ar.moviesapp.data.remote.model.request.MovieRequest
import com.ar.moviesapp.data.remote.model.response.NowPlayingMovieResponse

interface MovieApi {
    suspend fun getNowPlayingMovies(request: MovieRequest): Result<NowPlayingMovieResponse, NetworkError>
}