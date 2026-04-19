package com.github.rodionk77.cinemajournalkmp.di

import com.github.rodionk77.cinemajournalkmp.backendHost
import com.github.rodionk77.cinemajournalkmp.data.MovieRepository
import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.RecommendationsRepository
import com.github.rodionk77.cinemajournalkmp.data.UsersRepository
import com.github.rodionk77.cinemajournalkmp.data.database.AppDatabase
import com.github.rodionk77.cinemajournalkmp.data.database.UsersDAO
import com.example.cinemajournal.ui.theme.screens.viewmodels.AdditionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.RecommendationsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import com.example.cinemajournal.Domain.movieUseCases.GetFilteredMoviesUseCase
import com.example.cinemajournal.Domain.recommendationUseCases.GenerateRecommendationsUseCase
import com.example.cinemajournal.Domain.recommendationUseCases.GetLatestRecommendationsUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetMovieByIdUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetSearchMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20InterestedKidsMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20InterestedMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20KidsMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20KidsSeriesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20MoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20SeriesUseCase
import com.example.cinemajournal.Domain.movieUseCases.RefreshMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.RefreshSearchMoviesUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToWatchToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddReviewToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddWatchedMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckMovieToWatchUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckWatchedMovieUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteAllDislikesFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteAllLikesFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteAllMoviesFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteAllMoviesToWatchFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteAllReviewsFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteAllWatchedMoviesFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteDislikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteLikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteReviewFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetAllDislikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetAllLikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetAllMoviesFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetAllMoviesToWatchByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetAllReviewsByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetAllWatchedMoviesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetCountriesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetDislikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetGenresByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetLikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMovieFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMoviesFromToWatchFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMoviesFromWatchedFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetPersonsByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetReminderDateAndTimeFromToWatchFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetReviewByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetSeasonsInfoByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetUserFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveCountriesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveDislikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveGenresToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveLikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveMovieToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveMovieToWatchToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SavePersonsToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveReviewToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveSeasonsInfoToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveWatchedMovieToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.UpdateMovieToWatchToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.UpdateReviewToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.UpdateWatchedMovieToDBUseCase
import com.example.cinemajournal.Domain.userUseCases.DeleteUserByIdUseCase
import com.example.cinemajournal.Domain.userUseCases.GetAllUsersUseCase
import com.example.cinemajournal.Domain.userUseCases.SaveUserToDatabaseUseCase
import com.example.cinemajournal.Domain.userUseCases.SignInUserUseCase
import com.example.cinemajournal.Domain.userUseCases.SignOutUserUseCase
import com.example.cinemajournal.Domain.userUseCases.SignUpUserUseCase
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {

    single(named("movies")) {
        HttpClient {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) { Napier.d(message, tag = "Ktor/Movies") }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.poiskkino.dev"
                }
            }
        }
    }

    // Android emulator: 10.0.2.2, iOS simulator: localhost, real device: Mac IP
    single(named("auth")) {
        HttpClient {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) { Napier.d(message, tag = "Ktor/Auth") }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = backendHost
                    port = 8080
                }
            }
        }
    }

    single(named("moviesdb")) {
        HttpClient {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) { Napier.d(message, tag = "Ktor/MoviesDB") }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = backendHost
                    port = 8090
                }
            }
        }
    }

    single(named("recommendations")) {
        HttpClient {
            install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) { Napier.d(message, tag = "Ktor/AI") }
                }
                level = LogLevel.ALL
            }
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTP
                    host = backendHost
                    port = 8092
                }
            }
        }
    }
}

val repositoryModule = module {
    single<UsersDAO> { get<AppDatabase>().usersDao() }
    single<MovieRepository> { MovieRepository(get(named("movies"))) }
    single<MoviesDBRepository> { MoviesDBRepository(get(), get(named("moviesdb"))) }
    single<UsersRepository> { UsersRepository(get(), get(named("auth"))) }
    single<RecommendationsRepository> { RecommendationsRepository(get(named("recommendations"))) }
}

val useCaseModule = module {
    // movieUseCases
    factory { GetFilteredMoviesUseCase(get()) }
    factory { GetMovieByIdUseCase(get()) }
    factory { GetSearchMoviesUseCase(get()) }
    factory { GetTop20InterestedKidsMoviesUseCase(get()) }
    factory { GetTop20InterestedMoviesUseCase(get()) }
    factory { GetTop20KidsMoviesUseCase(get()) }
    factory { GetTop20KidsSeriesUseCase(get()) }
    factory { GetTop20MoviesUseCase(get()) }
    factory { GetTop20SeriesUseCase(get()) }
    factory { RefreshMoviesUseCase(get()) }
    factory { RefreshSearchMoviesUseCase(get()) }

    // moviesDBUseCases
    factory { AddMovieToDBUseCase(get()) }
    factory { AddMovieToWatchToDBUseCase(get()) }
    factory { AddReviewToDBUseCase(get()) }
    factory { AddWatchedMovieToDBUseCase(get()) }
    factory { CheckMovieToWatchUseCase(get()) }
    factory { CheckWatchedMovieUseCase(get()) }
    factory { DeleteAllDislikesFromLocalDBUseCase(get()) }
    factory { DeleteAllLikesFromLocalDBUseCase(get()) }
    factory { DeleteAllMoviesFromLocalDBUseCase(get()) }
    factory { DeleteAllMoviesToWatchFromLocalDBUseCase(get()) }
    factory { DeleteAllReviewsFromLocalDBUseCase(get()) }
    factory { DeleteAllWatchedMoviesFromLocalDBUseCase(get()) }
    factory { DeleteDislikesByIdFromLocalDBUseCase(get()) }
    factory { DeleteLikesByIdFromLocalDBUseCase(get()) }
    factory { DeleteMovieByIdFromLocalDBUseCase(get()) }
    factory { DeleteMovieToWatchByIdFromLocalDBUseCase(get()) }
    factory { DeleteMovieToWatchFromDBUseCase(get()) }
    factory { DeleteReviewFromLocalDBUseCase(get()) }
    factory { DeleteWatchedMovieByIdFromLocalDBUseCase(get()) }
    factory { DeleteWatchedMovieFromDBUseCase(get()) }
    factory { GetAllDislikesByIdFromLocalDBUseCase(get()) }
    factory { GetAllLikesByIdFromLocalDBUseCase(get()) }
    factory { GetAllMoviesFromLocalDBUseCase(get()) }
    factory { GetAllMoviesToWatchByIdFromLocalDBUseCase(get()) }
    factory { GetAllReviewsByIdFromLocalDBUseCase(get()) }
    factory { GetAllWatchedMoviesByIdFromLocalDBUseCase(get()) }
    factory { GetCountriesByIdFromLocalDBUseCase(get()) }
    factory { GetDislikesByIdFromLocalDBUseCase(get()) }
    factory { GetGenresByIdFromLocalDBUseCase(get()) }
    factory { GetLikesByIdFromLocalDBUseCase(get()) }
    factory { GetMovieByIdFromLocalDBUseCase(get()) }
    factory { GetMovieFromDBUseCase(get()) }
    factory { GetMoviesFromToWatchFromLocalDBUseCase(get()) }
    factory { GetMoviesFromWatchedFromLocalDBUseCase(get()) }
    factory { GetPersonsByIdFromLocalDBUseCase(get()) }
    factory { GetReminderDateAndTimeFromToWatchFromLocalDBUseCase(get()) }
    factory { GetReviewByIdFromLocalDBUseCase(get()) }
    factory { GetSeasonsInfoByIdFromLocalDBUseCase(get()) }
    factory { GetUserFromDBUseCase(get()) }
    factory { SaveCountriesToLocalDBUseCase(get()) }
    factory { SaveDislikesToLocalDBUseCase(get()) }
    factory { SaveGenresToLocalDBUseCase(get()) }
    factory { SaveLikesToLocalDBUseCase(get()) }
    factory { SaveMovieToLocalDBUseCase(get()) }
    factory { SaveMovieToWatchToLocalDBUseCase(get()) }
    factory { SavePersonsToLocalDBUseCase(get()) }
    factory { SaveReviewToLocalDBUseCase(get()) }
    factory { SaveSeasonsInfoToLocalDBUseCase(get()) }
    factory { SaveWatchedMovieToLocalDBUseCase(get()) }
    factory { UpdateMovieToWatchToDBUseCase(get()) }
    factory { UpdateReviewToDBUseCase(get()) }
    factory { UpdateWatchedMovieToDBUseCase(get()) }

    // recommendationUseCases
    factory { GenerateRecommendationsUseCase(get()) }
    factory { GetLatestRecommendationsUseCase(get()) }

    // userUseCases
    factory { DeleteUserByIdUseCase(get()) }
    factory { GetAllUsersUseCase(get()) }
    factory { SaveUserToDatabaseUseCase(get()) }
    factory { SignInUserUseCase(get()) }
    factory { SignOutUserUseCase(get()) }
    factory { SignUpUserUseCase(get()) }
}

val viewModelModule = module {
    viewModel { AdditionViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { GalleryViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { ReviewViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { DescriptionViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { JournalsViewModel(get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { AuthViewModel(get(), get(), get(), get(), get(), get()) }
    viewModel { RecommendationsViewModel(get(), get(), get()) }
}
