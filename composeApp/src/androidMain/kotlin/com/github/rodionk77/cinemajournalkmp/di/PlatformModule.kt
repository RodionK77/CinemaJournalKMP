package com.github.rodionk77.di

import com.github.rodionk77.cinemajournalkmp.data.database.AppDatabase
import com.github.rodionk77.cinemajournalkmp.getDatabaseBuilder
import com.github.rodionk77.common.database.getRoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val androidPlatformModule = module {
    single<AppDatabase> { getRoomDatabase(getDatabaseBuilder(androidContext())) }
}
