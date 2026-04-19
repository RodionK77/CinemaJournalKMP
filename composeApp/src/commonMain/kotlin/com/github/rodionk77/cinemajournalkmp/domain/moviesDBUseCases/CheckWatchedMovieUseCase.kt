package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository

class CheckWatchedMovieUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int, userId: Int): Boolean {
        return repository.checkWatchedMovie(movieId, userId)
    }
}