package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Likes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User

class GetAllLikesByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: User): List<Likes>? {
        return repository.getAllLikesByIdFromLocalDB(requestBody)
    }
}