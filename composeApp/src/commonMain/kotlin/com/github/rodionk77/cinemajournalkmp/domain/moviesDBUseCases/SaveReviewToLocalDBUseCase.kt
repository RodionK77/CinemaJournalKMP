package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Review

class SaveReviewToLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Review) {
        repository.saveReviewToLocalDB(requestBody)
    }
}