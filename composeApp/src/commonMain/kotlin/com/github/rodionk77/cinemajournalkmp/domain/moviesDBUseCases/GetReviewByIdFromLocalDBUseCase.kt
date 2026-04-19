package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Review

class GetReviewByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): Review{
        return repository.getReviewByIdFromLocalDB(movieId)
    }
}