package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class WatchedMoviesForRetrofit(
    val user: User? = null,
    val movie: RoomMovieInfoForRetrofit? = null,
    //var dateWatched: String? = null,
    val contentId: Int? = null
)