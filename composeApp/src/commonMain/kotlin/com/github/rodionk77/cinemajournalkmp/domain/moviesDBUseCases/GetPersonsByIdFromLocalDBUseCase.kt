package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.PersonsForRetrofit

class GetPersonsByIdFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): List<PersonsForRetrofit>{
        return repository.getPersonsByIdFromLocalDB(movieId)
    }
}