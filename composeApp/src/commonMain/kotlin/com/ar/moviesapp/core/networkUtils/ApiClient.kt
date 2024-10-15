package com.ar.moviesapp.core.networkUtils

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                json = Json {
                    ignoreUnknownKeys = true
                }
            )
        }
        defaultRequest {
            header("accept", "application/json")
            header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkNzY5ZDk5MGI3MmJhYzMyMzAxMDNmOWY4NzQ1MGVhMyIsIm5iZiI6MTcyODk2NzA1My4xOTE0NzIsInN1YiI6IjY3MGNmY2Q5ZDVmOTNhM2RhMGJiYjA0MiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EHuXfZ-sLDFx-HUhPTt27CfYFNkdYJsu_DKRh8GF0hU")
        }
    }
}