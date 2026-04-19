package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatch
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User

class GetAllMoviesToWatchByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: User): List<MoviesToWatch>? {
        return repository.getAllMoviesToWatchByIdFromLocalDB(requestBody)
    }
}