package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.ReviewForRetrofit

class AddReviewToDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: ReviewForRetrofit): ReviewForRetrofit? {
        return repository.addReviewToDB(requestBody)
    }
}