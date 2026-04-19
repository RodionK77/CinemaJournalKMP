package com.example.cinemajournal.Domain.movieUseCases

import com.github.rodionk77.cinemajournalkmp.data.MovieRepository
import com.github.rodionk77.cinemajournalkmp.data.models.MovieListResponse

class GetSearchMoviesUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(query: String): MovieListResponse?  {
        return repository.getSearchMovies(query = query)
    }
}