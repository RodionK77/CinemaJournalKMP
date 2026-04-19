package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToWatchToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddWatchedMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckMovieToWatchUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckWatchedMovieUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveCountriesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveGenresToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveMovieToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveMovieToWatchToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveWatchedMovieToLocalDBUseCase
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Countries
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.CountriesForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Genres
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.GenresForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatch
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatchForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMovies
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMoviesForRetrofit
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

data class AdditionUiState(
    val nameText: String = "",
    val yearText: String = "",
    val ratingText: Double = 0.0,
    val timeText: Int = 0,
    val ageRatingText: String = "",
    val budgetText: Int = 0,
    val feesWorldText: Long = 0,
    val descriptionText: String = "",
    var countries: MutableList<CountriesForRetrofit> = mutableListOf(),
    var genres: MutableList<GenresForRetrofit> = mutableListOf(),
    val responseId: Int? = null,
    val isAddingMovie: Boolean = false,
    val addMovieError: String? = null,
)

class AdditionViewModel (
    private val addMovieToDBUseCase: AddMovieToDBUseCase,
    private val addMovieToWatchToDBUseCase: AddMovieToWatchToDBUseCase,
    private val deleteMovieToWatchFromDBUseCase: DeleteMovieToWatchFromDBUseCase,
    private val addWatchedMovieToDBUseCase: AddWatchedMovieToDBUseCase,
    private val deleteWatchedMovieFromDBUseCase: DeleteWatchedMovieFromDBUseCase,
    private val checkMovieToWatchUseCase: CheckMovieToWatchUseCase,
    private val checkWatchedMovieUseCase: CheckWatchedMovieUseCase,
    private val saveMovieToWatchToLocalDBUseCase: SaveMovieToWatchToLocalDBUseCase,
    private val saveWatchedMoviesToLocalDBUseCase: SaveWatchedMovieToLocalDBUseCase,
    private val saveMovieToLocalDBUseCase: SaveMovieToLocalDBUseCase,
    private val saveCountriesToLocalDBUseCase: SaveCountriesToLocalDBUseCase,
    private val saveGenresToLocalDBUseCase: SaveGenresToLocalDBUseCase,
) : ViewModel() {

    var uiState by mutableStateOf(AdditionUiState())
        private set

    fun changeNameText(text: String){ uiState = uiState.copy(nameText = text) }
    fun changeYearText(text: String){ uiState = uiState.copy(yearText = text) }
    fun changeRatingText(text: Double){ uiState = uiState.copy(ratingText = text) }
    fun changeTimeText(text: Int){ uiState = uiState.copy(timeText = text) }
    fun changeAgeRatingText(text: String){ uiState = uiState.copy(ageRatingText = text) }
    fun changeBudgetText(text: Int){ uiState = uiState.copy(budgetText = text) }
    fun changeFeesWorldText(text: Long){ uiState = uiState.copy(feesWorldText = text) }
    fun changeDescriptionText(text: String){ uiState = uiState.copy(descriptionText = text) }
    fun changeCountries(countries: MutableList<CountriesForRetrofit>){ uiState = uiState.copy(countries = countries) }
    fun changeGenres(genres: MutableList<GenresForRetrofit>){ uiState = uiState.copy(genres = genres) }
    fun changeId(id: Int?){ uiState = uiState.copy(responseId = id) }

    fun addGenre(genre: GenresForRetrofit){ uiState = uiState.copy(genres = (uiState.genres + genre).toMutableList()) }
    fun addCountry(country: CountriesForRetrofit){ uiState = uiState.copy(countries = (uiState.countries + country).toMutableList()) }

    fun changeIdInLists(id: Int){
        for (country in uiState.countries) {
            country.movieId = id
            country.countriesId = id
        }
        for (genre in uiState.genres) {
            genre.movieId = id
            genre.contentId = id
        }
    }

    fun clearAllField(){
        uiState = uiState.copy(nameText = "", yearText = "", ratingText = 0.0, timeText = 0, ageRatingText = "", budgetText = 0, feesWorldText = 0, descriptionText = "", countries = mutableListOf(), genres = mutableListOf(), responseId = null)
    }

    fun addMovieToDB(roomMovieInfo: RoomMovieInfoForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addMovieToDBUseCase(roomMovieInfo) }
                .onSuccess { response ->
                    uiState = uiState.copy(responseId = response?.id ?: 0)
                    Napier.d(response.toString(), tag = "AdditionViewModel")
                }
                .onFailure { Napier.e("Проблема при добавлении фильма: ${it.message}", tag = "AdditionViewModel") }
        }
    }

    fun addMovieToWatchToDB(moviesToWatch: MoviesToWatchForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addMovieToWatchToDBUseCase(moviesToWatch) }
                .onSuccess { response ->
                    Napier.d(response.toString(), tag = "AdditionViewModel")
                }
                .onFailure { Napier.e("Проблема при добавлении фильма к просмотру: ${it.message}", tag = "AdditionViewModel") }
        }
    }

    fun addWatchedMovieToDB(watchedMovies: WatchedMoviesForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addWatchedMovieToDBUseCase(watchedMovies) }
                .onSuccess { response ->
                    Napier.d(response.toString(), tag = "AdditionViewModel")
                }
                .onFailure { Napier.e("Проблема при добавлении просмотренного филмьма: ${it.message}", tag = "AdditionViewModel") }
        }
    }

    fun addMovieToLocalDB(roomMovieInfo: RoomMovieInfo) {
        viewModelScope.launch {
            kotlin.runCatching { saveMovieToLocalDBUseCase(roomMovieInfo) }
                .onSuccess { Napier.d("Фильм записан в локальную БД", tag = "AdditionViewModel") }
                .onFailure { Napier.e("Проблема с записью фильма в локальную бд: ${it.message}", tag = "AdditionViewModel") }
        }
    }

    fun addWatchedMovieToLocalDB(watchedMovies: WatchedMovies) {
        viewModelScope.launch {
            kotlin.runCatching { saveWatchedMoviesToLocalDBUseCase(watchedMovies) }
                .onSuccess { Napier.d("Просмотренный записан в локальную БД", tag = "AdditionViewModel") }
                .onFailure { Napier.e("Проблема с записью просмотренного в локальную бд: ${it.message}", tag = "AdditionViewModel") }
        }
    }

    fun addMovieToWatchToLocalDB(toWatch: MoviesToWatch) {
        viewModelScope.launch {
            kotlin.runCatching { saveMovieToWatchToLocalDBUseCase(toWatch) }
                .onSuccess { Napier.d("К просмотру записан в локальную БД", tag = "AdditionViewModel") }
                .onFailure { Napier.e("Проблема с записью к просмотру в локальную бд: ${it.message}", tag = "AdditionViewModel") }
        }
    }

    fun writeCountriesToLocalDB(countries: List<com.github.rodionk77.cinemajournalkmp.data.models.Countries>, movieId: Int) {
        countries.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveCountriesToLocalDBUseCase(Countries(contentId = movieId, name = it.name ?: ""))
                }
                    .onSuccess { Napier.d("Страна записалась", tag = "AdditionViewModel") }
                    .onFailure { Napier.e("Страна не записалась: ${it.message}", tag = "AdditionViewModel") }
            }
        }
    }

    fun writeGenresToLocalDB(genres: List<com.github.rodionk77.cinemajournalkmp.data.models.Genres>, movieId: Int) {
        genres.forEach {
            viewModelScope.launch {
                kotlin.runCatching {
                    saveGenresToLocalDBUseCase(Genres(contentId = movieId, name = it.name ?: ""))
                }
                    .onSuccess { Napier.d("Жанр записался", tag = "AdditionViewModel") }
                    .onFailure { Napier.e("Жанр не записался: ${it.message}", tag = "AdditionViewModel") }
            }
        }
    }

    fun addToWatched(userId: Int) {
        viewModelScope.launch {
            Napier.d("addToWatched: start, userId=$userId, name='${uiState.nameText}'", tag = "AdditionViewModel")
            uiState = uiState.copy(isAddingMovie = true, addMovieError = null, responseId = null)

            val id = kotlin.runCatching {
                addMovieToDBUseCase(RoomMovieInfoForRetrofit(name = uiState.nameText))
            }.onFailure {
                Napier.e("addToWatched: failed to get ID: ${it.message}", tag = "AdditionViewModel")
                uiState = uiState.copy(isAddingMovie = false, addMovieError = it.message)
            }.getOrNull()?.id

            if (id == null) {
                Napier.e("addToWatched: no ID returned, aborting", tag = "AdditionViewModel")
                uiState = uiState.copy(isAddingMovie = false, addMovieError = "No ID returned")
                return@launch
            }
            Napier.d("addToWatched: got id=$id", tag = "AdditionViewModel")

            changeIdInLists(id)
            val movieInfo = buildMovieInfoForRetrofit(id)
            Napier.d("addToWatched: sending full movie: $movieInfo", tag = "AdditionViewModel")

            kotlin.runCatching { addMovieToDBUseCase(movieInfo) }
                .onFailure { Napier.e("addToWatched: update movie failed: ${it.message}", tag = "AdditionViewModel") }

            kotlin.runCatching {
                addWatchedMovieToDBUseCase(WatchedMoviesForRetrofit(user = com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User(id = userId), movie = movieInfo))
            }.onFailure { Napier.e("addToWatched: addWatchedMovie failed: ${it.message}", tag = "AdditionViewModel") }

            val roomMovie = buildRoomMovieInfo(id)
            kotlin.runCatching { saveMovieToLocalDBUseCase(roomMovie) }
                .onSuccess { Napier.d("addToWatched: movie saved to local DB", tag = "AdditionViewModel") }
                .onFailure { Napier.e("addToWatched: local save failed: ${it.message}", tag = "AdditionViewModel") }

            val countriesList = uiState.countries.map { com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Countries(contentId = id, name = it.name ?: "") }
            val genresList = uiState.genres.map { com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Genres(contentId = id, name = it.name ?: "") }
            countriesList.forEach { kotlin.runCatching { saveCountriesToLocalDBUseCase(it) } }
            genresList.forEach { kotlin.runCatching { saveGenresToLocalDBUseCase(it) } }

            kotlin.runCatching {
                saveWatchedMoviesToLocalDBUseCase(WatchedMovies(userId = userId, movieId = id))
            }.onSuccess { Napier.d("addToWatched: done", tag = "AdditionViewModel") }
             .onFailure { Napier.e("addToWatched: local watched save failed: ${it.message}", tag = "AdditionViewModel") }

            uiState = uiState.copy(isAddingMovie = false, responseId = id)
        }
    }

    fun addToWatch(userId: Int) {
        viewModelScope.launch {
            Napier.d("addToWatch: start, userId=$userId, name='${uiState.nameText}'", tag = "AdditionViewModel")
            uiState = uiState.copy(isAddingMovie = true, addMovieError = null, responseId = null)

            val id = kotlin.runCatching {
                addMovieToDBUseCase(RoomMovieInfoForRetrofit(name = uiState.nameText))
            }.onFailure {
                Napier.e("addToWatch: failed to get ID: ${it.message}", tag = "AdditionViewModel")
                uiState = uiState.copy(isAddingMovie = false, addMovieError = it.message)
            }.getOrNull()?.id

            if (id == null) {
                Napier.e("addToWatch: no ID returned, aborting", tag = "AdditionViewModel")
                uiState = uiState.copy(isAddingMovie = false, addMovieError = "No ID returned")
                return@launch
            }
            Napier.d("addToWatch: got id=$id", tag = "AdditionViewModel")

            changeIdInLists(id)
            val movieInfo = buildMovieInfoForRetrofit(id)
            Napier.d("addToWatch: sending full movie: $movieInfo", tag = "AdditionViewModel")

            kotlin.runCatching { addMovieToDBUseCase(movieInfo) }
                .onFailure { Napier.e("addToWatch: update movie failed: ${it.message}", tag = "AdditionViewModel") }

            kotlin.runCatching {
                addMovieToWatchToDBUseCase(MoviesToWatchForRetrofit(user = com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User(id = userId), movie = movieInfo))
            }.onFailure { Napier.e("addToWatch: addMovieToWatch failed: ${it.message}", tag = "AdditionViewModel") }

            val roomMovie = buildRoomMovieInfo(id)
            kotlin.runCatching { saveMovieToLocalDBUseCase(roomMovie) }
                .onSuccess { Napier.d("addToWatch: movie saved to local DB", tag = "AdditionViewModel") }
                .onFailure { Napier.e("addToWatch: local save failed: ${it.message}", tag = "AdditionViewModel") }

            val countriesList = uiState.countries.map { com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Countries(contentId = id, name = it.name ?: "") }
            val genresList = uiState.genres.map { com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Genres(contentId = id, name = it.name ?: "") }
            countriesList.forEach { kotlin.runCatching { saveCountriesToLocalDBUseCase(it) } }
            genresList.forEach { kotlin.runCatching { saveGenresToLocalDBUseCase(it) } }

            kotlin.runCatching {
                saveMovieToWatchToLocalDBUseCase(MoviesToWatch(userId = userId, movieId = id))
            }.onSuccess { Napier.d("addToWatch: done", tag = "AdditionViewModel") }
             .onFailure { Napier.e("addToWatch: local toWatch save failed: ${it.message}", tag = "AdditionViewModel") }

            uiState = uiState.copy(isAddingMovie = false, responseId = id)
        }
    }

    private fun buildMovieInfoForRetrofit(id: Int) = RoomMovieInfoForRetrofit(
        id = id,
        name = uiState.nameText,
        feesWorld = uiState.feesWorldText,
        budget = uiState.budgetText,
        worldPremier = uiState.yearText,
        kpRating = uiState.ratingText,
        typeNumber = 1,
        movieLength = uiState.timeText,
        description = uiState.descriptionText,
        year = if (uiState.yearText.isNotEmpty()) uiState.yearText.toInt() else 0,
        ageRating = uiState.ageRatingText,
        countriesStr = uiState.countries.mapNotNull { it.name }.joinToString(", "),
        genresStr = uiState.genres.mapNotNull { it.name }.joinToString(", "),
    )

    private fun buildRoomMovieInfo(id: Int) = RoomMovieInfo(
        id = id,
        name = uiState.nameText,
        feesWorld = uiState.feesWorldText,
        budget = uiState.budgetText,
        worldPremier = uiState.yearText,
        kpRating = uiState.ratingText,
        typeNumber = 1,
        movieLength = uiState.timeText,
        description = uiState.descriptionText,
        year = if (uiState.yearText.isNotEmpty()) uiState.yearText.toInt() else 0,
        ageRating = uiState.ageRatingText,
    )

}
