package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetMovieByIdUseCase
import com.example.cinemajournal.Domain.recommendationUseCases.GenerateRecommendationsUseCase
import com.example.cinemajournal.Domain.recommendationUseCases.GetLatestRecommendationsUseCase
import com.github.rodionk77.cinemajournalkmp.data.models.MovieInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RecommendationResponse
import io.github.aakira.napier.Napier
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

data class RecommendationsUiState(
    val currentUserId: Long = -1L,
    val latestRecommendation: RecommendationResponse? = null,
    val recommendedMoviesMap: Map<Int, MovieInfo> = emptyMap(),
    val isLoadingInitial: Boolean = false,
    val isGenerating: Boolean = false,
    val errorMessage: String? = null,
    val hasCheckedInitial: Boolean = false
)

class RecommendationsViewModel(
    private val getLatestRecommendationsUseCase: GetLatestRecommendationsUseCase,
    private val generateRecommendationsUseCase: GenerateRecommendationsUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase
) : ViewModel() {

    var uiState by mutableStateOf(RecommendationsUiState())
        private set

    fun loadLatestRecommendations(userId: Long) {
        if (uiState.hasCheckedInitial && uiState.currentUserId == userId) return
        // Сброс состояния при смене пользователя
        uiState = RecommendationsUiState(currentUserId = userId, isLoadingInitial = true)
        viewModelScope.launch {
            kotlin.runCatching { getLatestRecommendationsUseCase(userId) }
                .onSuccess { recommendation ->
                    if (recommendation != null) {
                        val moviesMap = enrichWithMovieDetails(recommendation)
                        uiState = uiState.copy(
                            currentUserId = userId,
                            latestRecommendation = recommendation,
                            recommendedMoviesMap = moviesMap,
                            isLoadingInitial = false,
                            hasCheckedInitial = true
                        )
                    } else {
                        uiState = uiState.copy(
                            currentUserId = userId,
                            latestRecommendation = null,
                            recommendedMoviesMap = emptyMap(),
                            isLoadingInitial = false,
                            hasCheckedInitial = true
                        )
                    }
                }
                .onFailure { e ->
                    Napier.e("loadLatestRecommendations error: ${e.message}", tag = "RecommendationsVM")
                    uiState = uiState.copy(
                        currentUserId = userId,
                        isLoadingInitial = false,
                        hasCheckedInitial = true,
                        errorMessage = e.message
                    )
                }
        }
    }

    fun generateRecommendations(userId: Long) {
        uiState = uiState.copy(isGenerating = true, errorMessage = null)
        viewModelScope.launch {
            kotlin.runCatching { generateRecommendationsUseCase(userId) }
                .onSuccess { recommendation ->
                    val moviesMap = enrichWithMovieDetails(recommendation)
                    uiState = uiState.copy(
                        latestRecommendation = recommendation,
                        recommendedMoviesMap = moviesMap,
                        isGenerating = false
                    )
                }
                .onFailure { e ->
                    Napier.e("generateRecommendations error: ${e.message}", tag = "RecommendationsVM")
                    uiState = uiState.copy(
                        isGenerating = false,
                        errorMessage = e.message
                    )
                }
        }
    }

    private suspend fun enrichWithMovieDetails(recommendation: RecommendationResponse): Map<Int, MovieInfo> {
        return coroutineScope {
            recommendation.items.map { item ->
                async {
                    val movie = kotlin.runCatching { getMovieByIdUseCase(item.movieId) }.getOrNull()
                    if (movie != null) item.movieId to movie else null
                }
            }.awaitAll().filterNotNull().toMap()
        }
    }

    fun clearError() {
        uiState = uiState.copy(errorMessage = null)
    }
}
