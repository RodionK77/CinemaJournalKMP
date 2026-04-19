package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Likes

class GetLikesByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): List<Likes>{
        return repository.getLikesByIdFromLocalDB(movieId)
    }
}