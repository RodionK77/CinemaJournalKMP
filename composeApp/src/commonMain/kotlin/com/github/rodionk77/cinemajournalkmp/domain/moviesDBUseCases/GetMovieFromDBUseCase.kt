package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfoForRetrofit

class GetMovieFromDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(id: Int): RoomMovieInfoForRetrofit? {
        return repository.getMovie(id)
    }
}