package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetMovieByIdUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckMovieToWatchUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckWatchedMovieUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteAllMoviesFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetCountriesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetDislikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetGenresByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetLikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMovieFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMoviesFromToWatchFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetMoviesFromWatchedFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetPersonsByIdFromLocalDBUseCase
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
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Countries
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.CountriesForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Dislikes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Genres
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.GenresForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Likes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatch
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Persons
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.PersonsForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Review
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.ReviewForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.UserForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMovies
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class JournalsUiState(
    val user: UserForRetrofit? = null,
    val selectedTabIndex: Int = 0,
    val moviesToWatch: List<RoomMovieInfoForRetrofit>? = null,
    val watchedMovies: List<RoomMovieInfoForRetrofit>? = null,
    val countries: List<CountriesForRetrofit>? = null,
    val genres: List<GenresForRetrofit>? = null,
    val persons: List<Persons>? = null,
    val seasonsInfo: List<SeasonsInfo>? = null,
    val downloadMoviesStatus: Boolean = false,
    val downloadUsersMoviesStatus: Boolean = false,
    val downloadReviewsStatus: Boolean = false,
    val downloadWatchedMovieStatus: Boolean = false,
    val accountDialogState: Boolean = false,
    val isRefreshing: Boolean = false
    )

class JournalsViewModel (
    private val getUserFromDBUseCase: GetUserFromDBUseCase,
    private val saveReviewToLocalDBUseCase: SaveReviewToLocalDBUseCase,
    private val saveLikesToLocalDBUseCase: SaveLikesToLocalDBUseCase,
    private val saveDislikesToLocalDBUseCase: SaveDislikesToLocalDBUseCase,
    private val saveMovieToWatchToLocalDBUseCase: SaveMovieToWatchToLocalDBUseCase,
    private val saveWatchedMoviesToLocalDBUseCase: SaveWatchedMovieToLocalDBUseCase,
    private val saveMovieToLocalDBUseCase: SaveMovieToLocalDBUseCase,
    private val saveCountriesToLocalDBUseCase: SaveCountriesToLocalDBUseCase,
    private val saveGenresToLocalDBUseCase: SaveGenresToLocalDBUseCase,
    private val savePersonsToLocalDBUseCase: SavePersonsToLocalDBUseCase,
    private val saveSeasonsInfoToLocalDBUseCase: SaveSeasonsInfoToLocalDBUseCase,
    private val checkMovieToWatchUseCase: CheckMovieToWatchUseCase,
    private val checkWatchedMovieUseCase: CheckWatchedMovieUseCase,
    private val getMoviesFromToWatchFromLocalDBUseCase: GetMoviesFromToWatchFromLocalDBUseCase,
    private val getMoviesFromWatchedFromLocalDBUseCase: GetMoviesFromWatchedFromLocalDBUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val getMovieFromDBUseCase: GetMovieFromDBUseCase,
    private val getMovieByIdFromLocalDBUseCase: GetMovieByIdFromLocalDBUseCase,
    private val deleteAllMoviesFromLocalDBUseCase: DeleteAllMoviesFromLocalDBUseCase,
    private val deleteMovieByIdFromLocalDBUseCase: DeleteMovieByIdFromLocalDBUseCase,
    private val deleteWatchedMovieByIdFromLocalDBUseCase: DeleteWatchedMovieByIdFromLocalDBUseCase,
    private val deleteMovieToWatchByIdFromLocalDBUseCase: DeleteMovieToWatchByIdFromLocalDBUseCase,
    private val addMovieToLocalDBUseCase: SaveMovieToLocalDBUseCase,
    private val addWatchedMovieToLocalDBUseCase: SaveWatchedMovieToLocalDBUseCase,
    private val addMovieToWatchToLocalDBUseCase: SaveMovieToWatchToLocalDBUseCase,
    private val getCountriesByIdFromLocalDBUseCase: GetCountriesByIdFromLocalDBUseCase,
    private val getGenresByIdFromLocalDBUseCase: GetGenresByIdFromLocalDBUseCase,
    private val getPersonsByIdFromLocalDBUseCase: GetPersonsByIdFromLocalDBUseCase,
    private val getSeasonsInfoByIdFromLocalDBUseCase: GetSeasonsInfoByIdFromLocalDBUseCase,
    private val getReviewByIdFromLocalDBUseCase: GetReviewByIdFromLocalDBUseCase,
    private val getLikesByIdFromLocalDBUseCase: GetLikesByIdFromLocalDBUseCase,
    private val getDislikesByIdFromLocalDBUseCase: GetDislikesByIdFromLocalDBUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(JournalsUiState())
        private set

    fun changeUser(user: UserForRetrofit?) {
        uiState = uiState.copy(user = user)
    }

    fun changeSelectedTabIndex(id: Int) {
        uiState = uiState.copy(selectedTabIndex = id)
    }

    fun changeAccountDialogState(b: Boolean) {
        uiState = uiState.copy(accountDialogState = b)
    }

    fun refreshData() {
        if (uiState.isRefreshing) return
        uiState = uiState.copy(isRefreshing = true, watchedMovies = null, moviesToWatch = null)
        viewModelScope.launch {
            delay(400)
            uiState = uiState.copy(isRefreshing = false)
        }
    }

    fun getUserFromDB(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching { getUserFromDBUseCase(id) }
                .onSuccess { response ->
                    uiState = uiState.copy(user = response)
                }
                .onFailure {
                    Napier.e("${id}; Проблема с получением данных пользователя из бд: ${it.message}", tag = "JournalsViewModel")
                }
        }
    }

    fun getMoviesFromToWatchFromLocalDB() {
        var movies: MutableList<RoomMovieInfoForRetrofit> = mutableListOf()
        var countries: List<CountriesForRetrofit> = mutableListOf()
        var genres: List<GenresForRetrofit> = mutableListOf()
        var persons: List<PersonsForRetrofit> = mutableListOf()
        var seasonsInfo: List<SeasonsInfoForRetrofit> = mutableListOf()
        viewModelScope.launch {
            kotlin.runCatching { getMoviesFromToWatchFromLocalDBUseCase() }
                .onSuccess { response ->
                    response?.forEach {
                        viewModelScope.launch {
                            kotlin.runCatching { getCountriesByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response2 ->
                                    Napier.d("Страны для фильма к просмотру получены: ${response2}", tag = "JournalsViewModel")
                                    countries = response2
                                }
                                .onFailure {
                                    Napier.e("Страны для фильма к просмотру не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getGenresByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response3 ->
                                    Napier.d("Жанры для фильма к просмотру получены: ${response3}", tag = "JournalsViewModel")
                                    genres = response3
                                }
                                .onFailure {
                                    Napier.e("Жанры для фильма к просмотру не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getPersonsByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response4 ->
                                    Napier.d("Персоны для фильма к просмотру получены: ${response4}", tag = "JournalsViewModel")
                                    persons = response4
                                }
                                .onFailure {
                                    Napier.e("Персоны для фильма к просмотру не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getSeasonsInfoByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response5 ->
                                    Napier.d("Сезоны для фильма к просмотру получены: ${response5}", tag = "JournalsViewModel")
                                    seasonsInfo = response5
                                }
                                .onFailure {
                                    Napier.e("Сезоны для фильма к просмотру не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        delay(250)
                        movies.add(
                            RoomMovieInfoForRetrofit(
                                id = it.id ?: 0,
                                name = it?.name ?: "",
                                feesWorld = it?.feesWorld ?: 0,
                                feesUsa = it?.feesUsa ?: 0,
                                budget = it?.budget ?: 0,
                                posterUrl = it?.posterUrl ?: "",
                                worldPremier = it?.worldPremier,
                                russiaPremier = it?.russiaPremier,
                                kpRating = it?.kpRating ?: 0.0,
                                imdbRating = it?.imdbRating ?: 0.0,
                                movieLength = it?.movieLength ?: 0,
                                type = it?.type ?: "",
                                typeNumber = it?.typeNumber ?: 0,
                                description = it?.description ?: "",
                                year = it?.year ?: 0,
                                alternativeName = it?.alternativeName ?: "",
                                enName = it?.enName ?: "",
                                ageRating = it?.ageRating.toString(),
                                isSeries = it?.isSeries,
                                seriesLength = it?.seriesLength,
                                totalSeriesLength = it?.totalSeriesLength,
                                countries = countries,
                                genres = genres,
                                persons = persons,
                                seasonsInfo = seasonsInfo,
                            )
                        )
                    }
                    uiState = uiState.copy(moviesToWatch = movies)
                }
                .onFailure {
                    Napier.e("Проблема с получением фильмов к просмотру: ${it.message}", tag = "JournalsViewModel")
                }
        }
    }

    fun getMoviesFromWatchedFromLocalDB() {
        var movies: MutableList<RoomMovieInfoForRetrofit> = mutableListOf()
        var countries: List<CountriesForRetrofit> = mutableListOf()
        var genres: List<GenresForRetrofit> = mutableListOf()
        var persons: List<PersonsForRetrofit> = mutableListOf()
        var seasonsInfo: List<SeasonsInfoForRetrofit> = mutableListOf()
        var review: Review? = null
        var likes: List<Likes> = mutableListOf()
        var dislikes: List<Dislikes> = mutableListOf()
        viewModelScope.launch {
            kotlin.runCatching { getMoviesFromWatchedFromLocalDBUseCase() }
                .onSuccess { response ->
                    response?.forEach {
                        viewModelScope.launch {
                            kotlin.runCatching { getCountriesByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response2 ->
                                    Napier.d("Страны для просмотренного фильма получены", tag = "JournalsViewModel")
                                    countries = response2
                                }
                                .onFailure {
                                    Napier.e("Страны для просмотренного фильма не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getGenresByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response3 ->
                                    Napier.d("Жанры для просмотренного фильма получены", tag = "JournalsViewModel")
                                    genres = response3
                                }
                                .onFailure {
                                    Napier.e("Жанры для просмотренного фильма не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getPersonsByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response4 ->
                                    Napier.d("Персоны для просмотренного фильма получены: ${response4}", tag = "JournalsViewModel")
                                    persons = response4
                                }
                                .onFailure {
                                    Napier.e("Персоны для просмоторенного не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getSeasonsInfoByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response5 ->
                                    Napier.d("Сезоны для просмотренного получены: ${response5}", tag = "JournalsViewModel")
                                    seasonsInfo = response5
                                }
                                .onFailure {
                                    Napier.e("Сезоны для просмотренного не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        viewModelScope.launch {
                            kotlin.runCatching { getReviewByIdFromLocalDBUseCase(it.id) }
                                .onSuccess { response6 ->
                                    Napier.d("Ревью для просмотенного получены: ${response6}", tag = "JournalsViewModel")
                                    review = response6

                                    viewModelScope.launch {
                                        delay(250)
                                        kotlin.runCatching { getLikesByIdFromLocalDBUseCase(it.id) }
                                            .onSuccess { response7 ->
                                                Napier.d("Лайки для просмотренного получены: ${response7}", tag = "JournalsViewModel")
                                                likes = response7
                                            }
                                            .onFailure {
                                                Napier.e("Лайки для просомтренного не получены: ${it.message}", tag = "JournalsViewModel")
                                            }
                                    }

                                    viewModelScope.launch {
                                        delay(250)
                                        kotlin.runCatching { getDislikesByIdFromLocalDBUseCase(it.id) }
                                            .onSuccess { response8 ->
                                                Napier.d("Дизлайки для просмотренного получены: ${response8}", tag = "JournalsViewModel")
                                                dislikes = response8
                                            }
                                            .onFailure {
                                                Napier.e("Дизлайки просмотренного не получены: ${it.message}", tag = "JournalsViewModel")
                                            }
                                    }
                                }
                                .onFailure {
                                    Napier.e("Ревью для просмотренного не получены: ${it.message}", tag = "JournalsViewModel")
                                }
                        }
                        delay(500)
                        val movie = RoomMovieInfoForRetrofit(
                            id = it.id ?: 0,
                            name = it?.name ?: "",
                            feesWorld = it?.feesWorld ?: 0,
                            feesUsa = it?.feesUsa ?: 0,
                            budget = it?.budget ?: 0,
                            posterUrl = it?.posterUrl ?: "",
                            worldPremier = it?.worldPremier,
                            russiaPremier = it?.russiaPremier,
                            kpRating = it?.kpRating ?: 0.0,
                            imdbRating = it?.imdbRating ?: 0.0,
                            movieLength = it?.movieLength ?: 0,
                            type = it?.type ?: "",
                            typeNumber = it?.typeNumber ?: 0,
                            description = it?.description ?: "",
                            year = it?.year ?: 0,
                            alternativeName = it?.alternativeName ?: "",
                            enName = it?.enName ?: "",
                            ageRating = it?.ageRating.toString(),
                            isSeries = it?.isSeries,
                            seriesLength = it?.seriesLength,
                            totalSeriesLength = it?.totalSeriesLength,
                            countries = countries,
                            genres = genres,
                            persons = persons,
                            seasonsInfo = seasonsInfo,
                            reviews = ReviewForRetrofit(
                                user = User(id = uiState.user?.id ?: 0),
                                movie = null,
                                contentId = it.id,
                                rating = review?.rating ?: 0.0,
                                notes = review?.notes ?: "",
                                likes = likes,
                                dislikes = dislikes,
                                dateWatched = review?.dateWatched
                            )
                        )
                        movies.add(movie)
                        Napier.d("Тот фильм: $movie", tag = "JournalsViewModel")
                        Napier.d("Ревью того фильма: ${movie.reviews}", tag = "JournalsViewModel")
                        countries = mutableListOf()
                        genres = mutableListOf()
                        persons = mutableListOf()
                        seasonsInfo = mutableListOf()
                        review = null
                        likes = emptyList()
                        dislikes = emptyList()
                    }
                    uiState = uiState.copy(watchedMovies = movies)
                }
                .onFailure {
                    Napier.e("Проблема с получением просмотренных фильмов: ${it.message}", tag = "JournalsViewModel")
                }
        }
    }

    fun deleteAllMoviesFromLocalDB() {
        viewModelScope.launch {
            kotlin.runCatching { deleteAllMoviesFromLocalDBUseCase() }
                .onSuccess { Napier.d("Фильмы удалены", tag = "JournalsViewModel") }
                .onFailure { Napier.e("Проблема с удалением фильмов: ${it.message}", tag = "JournalsViewModel") }
        }
    }

    fun deleteMovieByIdFromLocalDB(id: Int) {
        viewModelScope.launch {
            kotlin.runCatching { deleteMovieByIdFromLocalDBUseCase(id) }
                .onSuccess { Napier.d("Фильм удален", tag = "JournalsViewModel") }
                .onFailure { Napier.e("Проблема с удалением фильма: ${it.message}", tag = "JournalsViewModel") }
        }
    }

    fun deleteWatchedMovieByIdFromLocalDB(movieId: Int, userId: Int) {
        viewModelScope.launch {
            kotlin.runCatching { deleteWatchedMovieByIdFromLocalDBUseCase(movieId, userId) }
                .onSuccess { Napier.d("Просмотренный удален", tag = "JournalsViewModel") }
                .onFailure { Napier.e("Проблема с удалением просмотренного: ${it.message}", tag = "JournalsViewModel") }
        }
    }

    fun addMovieToLocalDB(roomMovieInfo: RoomMovieInfo) {
        viewModelScope.launch {
            kotlin.runCatching { addMovieToLocalDBUseCase(roomMovieInfo) }
                .onSuccess { Napier.d("Фильм записан в локальную БД", tag = "JournalsViewModel") }
                .onFailure { Napier.e("Проблема с записью фильма в локальную бд: ${it.message}", tag = "JournalsViewModel") }
        }
    }

    fun addWatchedMovieToLocalDB(watchedMovies: WatchedMovies) {
        viewModelScope.launch {
            kotlin.runCatching { addWatchedMovieToLocalDBUseCase(watchedMovies) }
                .onSuccess { Napier.d("Просмотренный записан в локальную БД", tag = "JournalsViewModel") }
                .onFailure { Napier.e("Проблема с записью просмотренного в локальную бд: ${it.message}", tag = "JournalsViewModel") }
        }
    }

    fun addMovieToWatchToLocalDB(toWatch: MoviesToWatch) {
        viewModelScope.launch {
            kotlin.runCatching { addMovieToWatchToLocalDBUseCase(toWatch) }
                .onSuccess { Napier.d("К просмотру записан в локальную БД", tag = "JournalsViewModel") }
                .onFailure { Napier.e("Проблема с записью к просмотру в локальную бд: ${it.message}", tag = "JournalsViewModel") }
        }
    }

    fun setCountries(countries: List<CountriesForRetrofit>?) {
        uiState = uiState.copy(countries = countries)
    }

    fun setGenres(genres: List<GenresForRetrofit>?) {
        uiState = uiState.copy(genres = genres)
    }

    fun startUpdateLocalDB(user: UserForRetrofit) {

        Napier.d("Заходим в стартовый апдейт", tag = "JournalsViewModel")

        viewModelScope.launch {

            loadAndWriteMovies(user)

            loadAndWriteUsersMovies(user)

            while (!uiState.downloadMoviesStatus && !uiState.downloadUsersMoviesStatus) {
                Napier.d("ждём загрузки фильмов", tag = "JournalsViewModel")
                delay(50)
            }

            writeWatchedMovies(user)

            writeMoviesToWatch(user)

            while (!uiState.downloadWatchedMovieStatus) {
                Napier.d("ждём загрузки просмотренных", tag = "JournalsViewModel")
                delay(50)
            }

            writeReviews(user)
        }

    }

    fun loadAndWriteMovies(user: UserForRetrofit) {
        Napier.d("уникальные фильмы: ${user.getAllUniqueMovies()}", tag = "JournalsViewModel")
        uiState = uiState.copy(downloadMoviesStatus = false)
        user.getAllUniqueMovies().filter { it < 19000000 }.forEach { movieId ->

            viewModelScope.launch {
                kotlin.runCatching { getMovieByIdUseCase(movieId) }
                    .onSuccess { movieInfo ->
                        Napier.d("Фильм из просмотренных загрузился: ${movieInfo!!.id}", tag = "JournalsViewModel")
                        viewModelScope.launch {
                            kotlin.runCatching {
                                saveMovieToLocalDBUseCase(
                                    RoomMovieInfo(
                                        id = movieInfo?.id ?: 0,
                                        name = movieInfo?.name ?: "",
                                        feesWorld = movieInfo?.fees?.world?.value ?: 0,
                                        feesUsa = movieInfo?.fees?.usa?.value ?: 0,
                                        budget = movieInfo?.budget?.value ?: 0,
                                        posterUrl = movieInfo?.poster?.url ?: "",
                                        worldPremier = movieInfo?.premiere?.world ?: "",
                                        russiaPremier = movieInfo?.premiere?.russia ?: "",
                                        kpRating = movieInfo?.rating?.kp ?: 0.0,
                                        imdbRating = movieInfo?.rating?.imdb ?: 0.0,
                                        movieLength = movieInfo?.movieLength ?: 0,
                                        type = movieInfo?.type ?: "",
                                        typeNumber = movieInfo?.typeNumber ?: 0,
                                        description = movieInfo?.description ?: "",
                                        year = movieInfo?.year ?: 0,
                                        alternativeName = movieInfo?.alternativeName ?: "",
                                        enName = movieInfo?.enName ?: "",
                                        ageRating = movieInfo?.ageRating.toString(),
                                        isSeries = movieInfo?.isSeries,
                                        seriesLength = movieInfo?.seriesLength.toString(),
                                        totalSeriesLength = movieInfo?.totalSeriesLength?.toString()
                                    )
                                )
                            }
                                .onSuccess {
                                    Napier.d("Фильм записался", tag = "JournalsViewModel")
                                    writeCountriesToLocalDB(movieInfo?.countries ?: emptyList(), movieId)
                                    writeGenresToLocalDB(movieInfo?.genres ?: emptyList(), movieId)
                                    writePersonsToLocalDB(movieInfo?.persons ?: emptyList(), movieId)
                                    writeSeasonsInfoToLocalDB(movieInfo?.seasonsInfo ?: emptyList(), movieId)
                                    if (movieId == user.getAllUniqueMovies().last()) {
                                        uiState = uiState.copy(downloadMoviesStatus = true)
                                        Napier.d("стейт: ${uiState.downloadMoviesStatus}", tag = "JournalsViewModel")
                                    }
                                }
                                .onFailure {
                                    Napier.e("Фильм не записался: ${it.message}", tag = "JournalsViewModel")
                                    if (movieId == user.getAllUniqueMovies().last()) {
                                        uiState = uiState.copy(downloadMoviesStatus = true)
                                    }
                                }
                        }
                    }
                    .onFailure {
                        viewModelScope.launch {
                            saveMovieToLocalDBUseCase(RoomMovieInfo(id = movieId))
                        }
                        Napier.e("Фильм не загрузился: ${it.message}", tag = "JournalsViewModel")
                        if (movieId == user.getAllUniqueMovies().last()) {
                            uiState = uiState.copy(downloadMoviesStatus = true)
                        }
                    }
            }
        }
    }

    fun loadAndWriteUsersMovies(user: UserForRetrofit) {
        Napier.d("уникальные фильмы: ${user.getAllUniqueMovies()}", tag = "JournalsViewModel")
        uiState = uiState.copy(downloadUsersMoviesStatus = false)
        user.getAllUniqueMovies().filter { it > 19000000 }.forEach { movieId ->

            viewModelScope.launch {
                kotlin.runCatching { getMovieFromDBUseCase(movieId) }
                    .onSuccess { movieInfo ->
                        Napier.d("Фильм из просмотренных загрузился: ${movieInfo!!.id}", tag = "JournalsViewModel")
                        viewModelScope.launch {
                            kotlin.runCatching {
                                saveMovieToLocalDBUseCase(
                                    RoomMovieInfo(
                                        id = movieInfo?.id ?: 0,
                                        name = movieInfo?.name ?: "",
                                        feesWorld = movieInfo?.feesWorld ?: 0,
                                        feesUsa = movieInfo?.feesUsa ?: 0,
                                        budget = movieInfo?.budget ?: 0,
                                        posterUrl = movieInfo?.posterUrl ?: "",
                                        worldPremier = movieInfo?.worldPremier ?: "",
                                        russiaPremier = movieInfo?.russiaPremier ?: "",
                                        kpRating = movieInfo?.kpRating ?: 0.0,
                                        imdbRating = movieInfo?.imdbRating ?: 0.0,
                                        movieLength = movieInfo?.movieLength ?: 0,
                                        type = movieInfo?.type ?: "",
                                        typeNumber = movieInfo?.typeNumber ?: 0,
                                        description = movieInfo?.description ?: "",
                                        year = movieInfo?.year ?: 0,
                                        alternativeName = movieInfo?.alternativeName ?: "",
                                        enName = movieInfo?.enName ?: "",
                                        ageRating = movieInfo?.ageRating.toString(),
                                        isSeries = movieInfo?.isSeries,
                                        seriesLength = movieInfo?.seriesLength,
                                        totalSeriesLength = movieInfo?.totalSeriesLength,
                                    )
                                )
                            }
                                .onSuccess {
                                    Napier.d("Фильм пользователя записался", tag = "JournalsViewModel")
                                    writeCountriesToLocalDB(
                                        (movieInfo?.countriesStr?.split(", ")
                                            ?.map { com.github.rodionk77.cinemajournalkmp.data.models.Countries(it) }
                                            ?: emptyList()),
                                        movieInfo.id!!
                                    )
                                    writeGenresToLocalDB(
                                        (movieInfo?.genresStr?.split(", ")
                                            ?.map { com.github.rodionk77.cinemajournalkmp.data.models.Genres(it) }
                                            ?: emptyList()),
                                        movieInfo.id!!
                                    )
                                    if (movieId == user.getAllUniqueMovies().last()) {
                                        uiState = uiState.copy(downloadUsersMoviesStatus = true)
                                        Napier.d("стейт: ${uiState.downloadMoviesStatus}", tag = "JournalsViewModel")
                                    }
                                }
                                .onFailure {
                                    Napier.e("Фильм пользователя не записался: ${it.message}", tag = "JournalsViewModel")
                                    if (movieId == user.getAllUniqueMovies().last()) {
                                        uiState = uiState.copy(downloadUsersMoviesStatus = true)
                                    }
                                }
                        }
                    }
                    .onFailure {
                        viewModelScope.launch {
                            saveMovieToLocalDBUseCase(RoomMovieInfo(id = movieId))
                        }
                        Napier.e("Фильм пользователя не загрузился: ${it.message}", tag = "JournalsViewModel")
                        if (movieId == user.getAllUniqueMovies().last()) {
                            uiState = uiState.copy(downloadUsersMoviesStatus = true)
                        }
                    }
            }
        }
    }

    fun writeCountriesToLocalDB(countries: List<com.github.rodionk77.cinemajournalkmp.data.models.Countries>, movieId: Int) {
        countries.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveCountriesToLocalDBUseCase(Countries(contentId = movieId, name = it.name ?: ""))
                }
                    .onSuccess { Napier.d("Страна записалась", tag = "JournalsViewModel") }
                    .onFailure { Napier.e("Страна не записалась: ${it.message}", tag = "JournalsViewModel") }
            }
        }
    }

    fun writeGenresToLocalDB(genres: List<com.github.rodionk77.cinemajournalkmp.data.models.Genres>, movieId: Int) {
        genres.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveGenresToLocalDBUseCase(Genres(contentId = movieId, name = it.name ?: ""))
                }
                    .onSuccess { Napier.d("Жанр записался", tag = "JournalsViewModel") }
                    .onFailure { Napier.e("Жанр не записался: ${it.message}", tag = "JournalsViewModel") }
            }
        }
    }

    fun writePersonsToLocalDB(persons: List<com.github.rodionk77.cinemajournalkmp.data.models.Persons>, movieId: Int) {
        persons.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    savePersonsToLocalDBUseCase(
                        Persons(
                            personsId = it.id!!,
                            contentId = movieId,
                            photo = it.photo,
                            name = it.name,
                            enName = it.enName,
                            description = it.description,
                            profession = it.profession,
                            enProfession = it.enProfession
                        )
                    )
                }
                    .onSuccess { Napier.d("Персона записалась", tag = "JournalsViewModel") }
                    .onFailure { Napier.e("Персгна не заисалась: ${it.message}", tag = "JournalsViewModel") }
            }
        }
    }

    fun writeSeasonsInfoToLocalDB(
        seasonsInfo: List<com.github.rodionk77.cinemajournalkmp.data.models.SeasonsInfo>,
        movieId: Int
    ) {
        seasonsInfo.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveSeasonsInfoToLocalDBUseCase(
                        SeasonsInfo(contentId = movieId, number = it.number ?: 0, episodesCount = it.episodesCount ?: 0)
                    )
                }
                    .onSuccess { Napier.d("Сезон записался", tag = "JournalsViewModel") }
                    .onFailure { Napier.e("Сезон не записался: ${it.message}", tag = "JournalsViewModel") }
            }
        }
    }

    fun writeWatchedMovies(user: UserForRetrofit) {
        uiState = uiState.copy(downloadWatchedMovieStatus = false)
        user.watchedMovies?.forEach { watchedMovie ->

            Napier.d("Просмотренный фильм: ${watchedMovie.contentId}", tag = "JournalsViewModel")

            viewModelScope.launch {
                delay(250)
                kotlin.runCatching {
                    saveWatchedMoviesToLocalDBUseCase(
                        WatchedMovies(userId = user.id, movieId = watchedMovie.contentId ?: 0)
                    )
                }
                    .onSuccess {
                        Napier.d("Просмотренные загрузились", tag = "JournalsViewModel")
                        if (watchedMovie == user.watchedMovies?.last()) {
                            uiState = uiState.copy(downloadWatchedMovieStatus = true)
                        }
                    }
                    .onFailure {
                        Napier.e("Просмотренные не загрузились: ${it.message}", tag = "JournalsViewModel")
                        if (watchedMovie == user.watchedMovies?.last()) {
                            uiState = uiState.copy(downloadWatchedMovieStatus = true)
                        }
                    }
            }
        }
    }

    fun writeMoviesToWatch(user: UserForRetrofit) {
        user.moviesToWatches?.forEach {
            viewModelScope.launch {
                delay(250)
                kotlin.runCatching {
                    saveMovieToWatchToLocalDBUseCase(
                        MoviesToWatch(
                            userId = user.id,
                            movieId = it.contentId ?: 0,
                            reminderDate = it.reminderDate,
                            reminderHour = it.reminderHour,
                            reminderMinute = it.reminderMinute
                        )
                    )
                }
                    .onSuccess { Napier.d("Фильмы к просмотру загрузились", tag = "JournalsViewModel") }
                    .onFailure { Napier.e("Фильмы к просмотру не загрузились: ${it.message}", tag = "JournalsViewModel") }
            }
        }
    }

    fun writeReviews(user: UserForRetrofit) {
        Napier.d("РРРевью: ${user.review}", tag = "JournalsViewModel")

        user.review?.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveReviewToLocalDBUseCase(
                        Review(
                            userId = user.id,
                            movieId = it.contentId,
                            rating = it.rating,
                            notes = it.notes,
                            dateWatched = it.dateWatched
                        )
                    )
                }
                    .onSuccess { Napier.d("Ревью записались: ${it}", tag = "JournalsViewModel") }
                    .onFailure { Napier.e("Ревью не записались: ${it.message}", tag = "JournalsViewModel") }
            }

            it.dislikes?.forEach {
                viewModelScope.launch {
                    kotlin.runCatching {
                        saveDislikesToLocalDBUseCase(
                            Dislikes(
                                dislikesId = it.dislikesId ?: 0,
                                userId = user.id,
                                movieId = it.contentId ?: 0,
                                description = it.description
                            )
                        )
                    }
                        .onSuccess { Napier.d("Дизлайки записались", tag = "JournalsViewModel") }
                        .onFailure { Napier.e("Дизлайки не записались: ${it.message}", tag = "JournalsViewModel") }
                }
            }

            it.likes?.forEach {
                viewModelScope.launch {
                    kotlin.runCatching {
                        saveLikesToLocalDBUseCase(
                            Likes(
                                likesId = it.likesId ?: 0,
                                userId = user.id,
                                movieId = it.contentId ?: 0,
                                description = it.description
                            )
                        )
                    }
                        .onSuccess { Napier.d("Лайки загрузились", tag = "JournalsViewModel") }
                        .onFailure { Napier.e("Лайки не загрузились: ${it.message}", tag = "JournalsViewModel") }
                }
            }
        }
    }
}
