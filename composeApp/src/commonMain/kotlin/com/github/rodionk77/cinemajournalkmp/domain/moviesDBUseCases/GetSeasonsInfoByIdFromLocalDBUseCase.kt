package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfoForRetrofit

class GetSeasonsInfoByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): List<SeasonsInfoForRetrofit> {
        return repository.getSeasonsInfoByIdFromLocalDB(movieId)
    }
}