package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class SeasonsInfoForRetrofit (
    //var seasonsId: Int,
    val contentId: Int?,
    val movieId: Int? = null,
    var number: Int = 0,
    var episodesCount: Int = 0,
)