package com.example.cinemajournal.Domain.movieUseCases

import com.github.rodionk77.cinemajournalkmp.data.MovieRepository
import com.github.rodionk77.cinemajournalkmp.data.models.MovieListResponse

class GetTop20KidsMoviesUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(): MovieListResponse?  {
        return repository.getTop20Kids()
    }
}