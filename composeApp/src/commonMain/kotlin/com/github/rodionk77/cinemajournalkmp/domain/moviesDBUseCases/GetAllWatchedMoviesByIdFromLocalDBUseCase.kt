package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMovies

class GetAllWatchedMoviesByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: User): List<WatchedMovies>? {
        return repository.getAllWatchedMoviesByIdFromLocalDB(requestBody)
    }
}