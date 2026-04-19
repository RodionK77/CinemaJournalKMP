package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class GenresForRetrofit (
    var genresId: Int?,
    var contentId: Int?,
    var name: String? = null,
    var movieId: Int? = null
)