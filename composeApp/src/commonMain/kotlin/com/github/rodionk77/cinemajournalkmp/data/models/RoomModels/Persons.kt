package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["personsId","content_id"],
    foreignKeys = [
        ForeignKey(
            entity = RoomMovieInfo::class,
            parentColumns = ["id"],
            childColumns = ["content_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Persons (
    var personsId: Int,
    @ColumnInfo(name = "content_id") val contentId: Int,
    var photo: String? = null,
    var name: String? = null,
    var enName: String? = null,
    var description: String? = null,
    var profession: String? = null,
    var enProfession: String? = null
)