package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfo

class SaveSeasonsInfoToLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: SeasonsInfo) {
        repository.saveSeasonsInfoToLocalDB(requestBody)
    }
}