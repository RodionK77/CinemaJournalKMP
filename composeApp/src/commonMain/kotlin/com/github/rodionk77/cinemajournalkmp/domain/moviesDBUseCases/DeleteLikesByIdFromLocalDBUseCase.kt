package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository

class DeleteLikesByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int) {
        repository.deleteLikesByIdFromLocalDB(movieId)
    }
}