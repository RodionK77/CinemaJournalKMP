package com.example.cinemajournal.Domain.movieUseCases

import com.github.rodionk77.cinemajournalkmp.data.MovieRepository

class RefreshSearchMoviesUseCase (private val repository: MovieRepository) {

    operator fun invoke(query: String)  {
        repository.refreshSearchMovies(query)
    }
}