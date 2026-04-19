package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class CountriesForRetrofit (
    var countriesId: Int?,
    val contentId: Int?,
    var name: String? = null,
    var movieId: Int? = null
)