package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfo

class GetMoviesFromToWatchFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(): List<RoomMovieInfo>? {
        return repository.getMoviesFromToWatchFromLocalDB()
    }
}