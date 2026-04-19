package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Review
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User

class GetAllReviewsByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: User): List<Review>? {
        return repository.getAllReviewsByIdFromLocalDB(requestBody)
    }
}