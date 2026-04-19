package com.github.rodionk77.cinemajournalkmp.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Countries
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Genres
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Dislikes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Likes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatch
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Persons
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Review
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMovies

@Database(
    entities = [User::class, RoomMovieInfo::class, MoviesToWatch::class, WatchedMovies::class, Review::class, Countries::class, Dislikes::class, Likes::class, Genres::class, Persons::class, SeasonsInfo::class],
    version = 1
)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDAO
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}