package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMovies

class SaveWatchedMovieToLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: WatchedMovies) {
        repository.saveWatchedMovieToLocalDB(requestBody)
    }
}