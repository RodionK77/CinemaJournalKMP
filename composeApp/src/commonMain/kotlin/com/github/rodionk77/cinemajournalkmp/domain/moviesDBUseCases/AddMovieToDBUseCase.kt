package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfoForRetrofit

class AddMovieToDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(requestBody: RoomMovieInfoForRetrofit): RoomMovieInfoForRetrofit? {
        return repository.addMovieToDB(requestBody)
    }
}