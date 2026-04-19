package com.example.cinemajournal.Domain.movieUseCases

import com.github.rodionk77.cinemajournalkmp.data.MovieRepository


class RefreshMoviesUseCase (private val repository: MovieRepository) {

    operator fun invoke()  {
        //repository.refreshMovies()
    }
}