package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatchForRetrofit

class DeleteMovieToWatchFromDBUseCase(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: MoviesToWatchForRetrofit) {
        repository.deleteMovieToWatchFromDB(requestBody)
    }
}
