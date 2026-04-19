package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.UserForRetrofit

class GetUserFromDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(id: Int): UserForRetrofit? {
        return repository.getUser(id)
    }
}