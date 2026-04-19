package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Review

class UpdateReviewToDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Review) {
        repository.updateReviewToDB(requestBody)
    }
}