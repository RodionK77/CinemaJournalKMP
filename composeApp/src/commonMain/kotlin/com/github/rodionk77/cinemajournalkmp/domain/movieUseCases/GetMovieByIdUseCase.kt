package com.example.cinemajournal.Domain.movieUseCases

import com.github.rodionk77.cinemajournalkmp.data.MovieRepository
import com.github.rodionk77.cinemajournalkmp.data.models.MovieInfo

class GetMovieByIdUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(id: Int): MovieInfo? {
        return repository.getMovieById(id)
    }
}