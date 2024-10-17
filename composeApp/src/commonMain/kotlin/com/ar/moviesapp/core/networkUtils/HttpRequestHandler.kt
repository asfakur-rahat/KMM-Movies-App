package com.ar.moviesapp.core.networkUtils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> callApiService(
    api: suspend () -> HttpResponse,
    onSuccess: (T) -> Unit,
    onError: (NetworkError) -> Unit,
) {
//    println("I Am call service")
    try {
        val response = api.invoke()
//        println("Response Status: ${response.status}")
//        println("Raw Response Body: ${response.bodyAsText()}") // Log raw response as text
        handleResult<T>(
            response,
            onSuccess = {
                println("Success Response: $it")
                onSuccess.invoke(it)
            },
            onError = { onError.invoke(it) }
        )
    } catch (e: UnresolvedAddressException) {
        return onError(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return onError(NetworkError.SERIALIZATION)
    } catch (e: Exception) {
        return onError(NetworkError.UNKNOWN)
    }
}

suspend inline fun <reified T> handleResult(
    result: HttpResponse,
    onSuccess: (T) -> Unit,
    onError: (NetworkError) -> Unit,
) {
//    println("I Am handle result")
    println("Response Status: ${result}")
    try {
        when (result.status.value) {
            in 200..299 -> {
                println("I Am in 200")
                try {
                    val responseBody: T = result.body()
                    println("Parsed Response: $responseBody")
                    onSuccess.invoke(responseBody)
                } catch (e: SerializationException) {
//                    println("Serialization error: ${e.message}")
                    onError(NetworkError.SERIALIZATION)
                } catch (e: Exception){
                    println("Unknown error: ${e.message}")
                    onError(NetworkError.UNKNOWN)
                }
            }

            401 -> onError(NetworkError.UNAUTHORIZED)
            409 -> onError(NetworkError.CONFLICT)
            408 -> onError(NetworkError.REQUEST_TIMEOUT)
            413 -> onError(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> onError(NetworkError.SERVER_ERROR)
            else -> onError(NetworkError.UNKNOWN)
        }
    } catch (e: Exception) {
        println("Unknown error: ${e.message}")
        onError(NetworkError.UNKNOWN)
    }
}
