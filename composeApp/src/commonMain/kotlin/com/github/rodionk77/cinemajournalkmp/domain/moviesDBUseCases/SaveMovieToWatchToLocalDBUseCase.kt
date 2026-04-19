package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatch

class SaveMovieToWatchToLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: MoviesToWatch) {
        repository.saveMovieToWatchToLocalDB(requestBody)
    }
}