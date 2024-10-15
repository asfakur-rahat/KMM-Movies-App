package com.ar.moviesapp.core.utils

fun String.toW500Image(): String {
    val baseUrl = "https://image.tmdb.org/t/p/"
    val size = "w500"
    val path = this
    return "$baseUrl$size$path"
}

fun String.toOriginalImage(): String {
    val baseUrl = "https://image.tmdb.org/t/p/"
    val size = "original"
    val path = this
    return "$baseUrl$size$path"
}

inline fun <reified T : Any> Any.cast(): T {
    return this as T
}