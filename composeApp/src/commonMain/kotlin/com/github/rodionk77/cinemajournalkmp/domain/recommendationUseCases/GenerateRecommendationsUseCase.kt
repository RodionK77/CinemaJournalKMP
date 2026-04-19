package com.example.cinemajournal.Domain.recommendationUseCases

import com.github.rodionk77.cinemajournalkmp.data.RecommendationsRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RecommendationResponse

class GenerateRecommendationsUseCase(private val repository: RecommendationsRepository) {

    suspend operator fun invoke(userId: Long): RecommendationResponse {
        return repository.generateRecommendations(userId)
    }
}
