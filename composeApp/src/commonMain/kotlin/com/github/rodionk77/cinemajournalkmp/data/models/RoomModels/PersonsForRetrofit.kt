package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class PersonsForRetrofit (
    var personsId: Int,
    val contentId: Int?,
    val movieId: Int? = null,
    var photo: String? = null,
    var name: String? = null,
    var enName: String? = null,
    var description: String? = null,
    var profession: String? = null,
    var enProfession: String? = null
)