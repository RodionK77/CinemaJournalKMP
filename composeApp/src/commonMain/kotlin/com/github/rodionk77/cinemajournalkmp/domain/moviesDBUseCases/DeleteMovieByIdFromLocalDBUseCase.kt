package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository

class DeleteMovieByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(id: Int) {
        repository.deleteMovieByIdFromLocalDB(id)
    }
}