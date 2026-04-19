package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Persons

class SavePersonsToLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Persons) {
        repository.savePersonsToLocalDB(requestBody)
    }
}