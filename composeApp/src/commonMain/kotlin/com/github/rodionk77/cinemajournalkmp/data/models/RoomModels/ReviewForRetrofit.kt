package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class ReviewForRetrofit(
    val user: User? = null,
    val movie: RoomMovieInfoForRetrofit? = null,
    var contentId: Int = 0,
    var rating: Double = 0.0,
    var notes: String? = null,
    var likes: List<Likes>? = null,
    var dislikes: List<Dislikes>? = null,
    var dateWatched: String? = null
)