package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository

class DeleteWatchedMovieByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(userId: Int, movieId: Int) {
        repository.deleteWatchedMovieByIdFromLocalDB(userId, movieId)
    }
}