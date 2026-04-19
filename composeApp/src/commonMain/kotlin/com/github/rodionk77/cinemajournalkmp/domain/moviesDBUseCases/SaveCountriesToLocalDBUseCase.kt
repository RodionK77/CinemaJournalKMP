package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Countries

class SaveCountriesToLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Countries) {
        repository.saveCountriesToLocalDB(requestBody)
    }
}