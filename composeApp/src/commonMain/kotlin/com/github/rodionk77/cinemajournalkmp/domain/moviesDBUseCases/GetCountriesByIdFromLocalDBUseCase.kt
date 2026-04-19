package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.CountriesForRetrofit

class GetCountriesByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): List<CountriesForRetrofit> {
        return repository.getCountriesByIdFromLocalDB(movieId)
    }
}