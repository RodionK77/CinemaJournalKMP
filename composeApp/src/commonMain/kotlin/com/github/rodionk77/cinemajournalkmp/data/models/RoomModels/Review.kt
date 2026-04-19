package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(
    primaryKeys = ["user_id","movie_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoomMovieInfo::class,
            parentColumns = ["id"],
            childColumns = ["movie_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Review(
    @ColumnInfo(name = "user_id") val userId: Int = 0,
    @ColumnInfo(name = "movie_id") val movieId: Int = 0,
    var contentId: Int = 0,
    var rating: Double = 0.0,
    var notes: String? = null,
    var dateWatched: String? = null

)