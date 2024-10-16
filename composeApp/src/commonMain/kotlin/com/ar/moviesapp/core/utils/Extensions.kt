package com.ar.moviesapp.core.utils

import com.ar.moviesapp.data.remote.model.response.Genre

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

fun Double.toRating(): String {
    return this.toString().substringBefore(".") + "." + this.toString().substringAfter(".").take(1)
}

fun String.onlyYear(): String{
    return this.substringBefore("-")
}

fun List<Genre>.toGenre(): String{
    var genre = "Not Assigned"
    if(this.isNotEmpty()){
        for(i in this){
            if(i.name != null){
                genre = i.name!!
                break
            }
        }
    }
    return genre
}