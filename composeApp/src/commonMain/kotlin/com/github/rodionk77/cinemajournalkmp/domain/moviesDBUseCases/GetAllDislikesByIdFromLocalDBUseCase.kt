package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Dislikes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User

class GetAllDislikesByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: User): List<Dislikes>? {
        return repository.getAllDislikesByIdFromLocalDB(requestBody)
    }
}