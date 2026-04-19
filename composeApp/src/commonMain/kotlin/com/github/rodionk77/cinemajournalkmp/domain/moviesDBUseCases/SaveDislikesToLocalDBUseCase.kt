package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Dislikes

class SaveDislikesToLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: Dislikes) {
        repository.saveDislikesToLocalDB(requestBody)
    }
}