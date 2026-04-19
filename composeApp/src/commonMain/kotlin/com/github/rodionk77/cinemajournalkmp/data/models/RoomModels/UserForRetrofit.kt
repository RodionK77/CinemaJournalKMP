package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class UserForRetrofit(
    var id: Int,
    var username: String? = "",
    var email: String? = "",
    var role: Int = 0,
    var review: Set<ReviewForRetrofit>? = hashSetOf(),
    var moviesToWatches: Set<MoviesToWatchForRetrofit>? = hashSetOf(),
    var watchedMovies: Set<WatchedMoviesForRetrofit>? = hashSetOf()
){
    fun getAllUniqueMovies(): Set<Int> {
        val uniqueMovies = mutableListOf<Int>()
        moviesToWatches?.forEach { uniqueMovies.add(it.contentId!!) }
        watchedMovies?.forEach { uniqueMovies.add(it.contentId!!) }
        return uniqueMovies.toSet()
    }

}