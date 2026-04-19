package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["content_id","name"],
    foreignKeys = [
        ForeignKey(
            entity = RoomMovieInfo::class,
            parentColumns = ["id"],
            childColumns = ["content_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class Countries (
    /*@PrimaryKey(autoGenerate = true)
    var countriesId: Int = 0,*/
    @ColumnInfo(name = "content_id") val contentId: Int,
    /*@PrimaryKey*/ var name: String,
)