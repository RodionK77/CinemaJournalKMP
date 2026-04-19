package com.github.rodionk77.cinemajournalkmp

import androidx.compose.ui.window.ComposeUIViewController
import com.github.rodionk77.cinemajournalkmp.di.networkModule
import com.github.rodionk77.cinemajournalkmp.di.repositoryModule
import com.github.rodionk77.cinemajournalkmp.di.useCaseModule
import com.github.rodionk77.cinemajournalkmp.di.viewModelModule
import com.github.rodionk77.cinemajournalkmp.presentation.screens.MainScreen
import com.github.rodionk77.cinemajournalkmp.presentation.theme.AppTheme
import com.github.rodionk77.di.iosPlatformModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.compose.KoinApplication

fun MainViewController() = ComposeUIViewController {
    Napier.base(DebugAntilog())
    KoinApplication(application = {
        modules(iosPlatformModule, networkModule, repositoryModule, useCaseModule,viewModelModule)
    }) {
        AppTheme {
            MainScreen()
        }
    }
}