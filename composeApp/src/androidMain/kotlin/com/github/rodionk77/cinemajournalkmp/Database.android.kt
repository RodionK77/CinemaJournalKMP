package com.github.rodionk77.cinemajournalkmp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.rodionk77.cinemajournalkmp.data.database.AppDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath("app_database.db")
    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}