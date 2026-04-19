package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository

class DeleteAllMoviesToWatchFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke() {
        repository.deleteAllMoviesToWatchFromLocalDB()
    }
}