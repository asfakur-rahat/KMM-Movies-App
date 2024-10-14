package com.ar.moviesapp.core.networkUtils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T> callApiService(
    api: suspend () -> HttpResponse,
    onSuccess: (T) -> Unit,
    onError: (NetworkError) -> Unit,
){

    try {
        val response = api.invoke()
        handleResult<T>(
            response,
            onSuccess = {
                onSuccess.invoke(it)
            },
            onError = {
                onError.invoke(it)
            }
        )
    } catch (e: UnresolvedAddressException) {
        return onError(NetworkError.NO_INTERNET)
    } catch (e: SerializationException) {
        return onError(NetworkError.SERIALIZATION)
    }

}


suspend inline fun <reified T> handleResult(
    result: HttpResponse,
    onSuccess: (T) -> Unit,
    onError: (NetworkError) -> Unit,
) {
    try {
        when (result.status.value) {
            in 200..299 -> {
                val responseBody: T = result.body()
                onSuccess.invoke(responseBody)
            }

            401 -> onError(NetworkError.UNAUTHORIZED)
            409 -> onError(NetworkError.CONFLICT)
            408 -> onError(NetworkError.REQUEST_TIMEOUT)
            413 -> onError(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> onError(NetworkError.SERVER_ERROR)
            else -> onError(NetworkError.UNKNOWN)
        }
    } catch (e: Exception) {
        onError(NetworkError.UNKNOWN)
    }
}