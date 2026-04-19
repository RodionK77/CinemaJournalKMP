package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class User(
    @PrimaryKey var id: Int,
    var username: String = "",
    var email: String = "",
    var role: Int = 0,
    //var watchedMovies: ArrayList<Review> = arrayListOf(),
    //var moviesToWatch: ArrayList<MovieInfo> = arrayListOf()
)
