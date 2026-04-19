package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class MoviesToWatchForRetrofit(
    val user: User? = null,
    val movie: RoomMovieInfoForRetrofit? = null,
    var contentId: Int? = null,
    var reminderDate: String? = null,
    var reminderHour: Int? = null,
    var reminderMinute: Int? = null,
)