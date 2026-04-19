package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMoviesForRetrofit

class DeleteWatchedMovieFromDBUseCase(private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: WatchedMoviesForRetrofit) {
        repository.deleteWatchedMovieFromDB(requestBody)
    }
}
