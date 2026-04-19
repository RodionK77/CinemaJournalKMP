package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

data class LikesForRetrofit (
    var likesId: Int?,
    val userId: Int?,
    val movie_id: Int?,
    var description: String? = null,
    val contentId: Int? = null
)