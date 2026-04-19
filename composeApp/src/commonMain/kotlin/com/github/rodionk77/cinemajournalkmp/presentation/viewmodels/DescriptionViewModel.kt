package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetMovieByIdUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddMovieToWatchToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.AddWatchedMovieToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckMovieToWatchUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.CheckWatchedMovieUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteMovieToWatchFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteWatchedMovieFromDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.GetReminderDateAndTimeFromToWatchFromLocalDBUseCase
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatchForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMoviesForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.MovieInfo
import io.github.aakira.napier.Napier
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class ItemDescriptionUiState(
    val movieInfo: MovieInfo? = null,
    val roomMovieInfoForRetrofit: RoomMovieInfoForRetrofit? = null,
    val user: User? = null,
    val movieToWatchStatus: Boolean = false,
    val watchedMovieStatus: Boolean = false,
    val addMovieToDBResponseMessage: String = "",
    val addMovieToWatchDBResponseMessage: String = "",
    val addWatchedMovieToDBResponseMessage: String = "",
    val addMovieToWatchToLocalDBResponseMessage: String = "",
    val addWatchedMovieToLocalDBResponseMessage: String = "",
    val reminderDialogStatus: Boolean = false,
    val dateToWatch: String? = null,
    val hoursToWatch: Int? = null,
    val minutesToWatch: Int? = null,
)

class DescriptionViewModel (private val getMovieByIdUseCase: GetMovieByIdUseCase,
                                               private val addMovieToDBUseCase: AddMovieToDBUseCase,
                                               private val addMovieToWatchToDBUseCase: AddMovieToWatchToDBUseCase,
                                               private val deleteMovieToWatchFromDBUseCase: DeleteMovieToWatchFromDBUseCase,
                                               private val addWatchedMovieToDBUseCase: AddWatchedMovieToDBUseCase,
                                               private val deleteWatchedMovieFromDBUseCase: DeleteWatchedMovieFromDBUseCase,
                                               private val checkMovieToWatchUseCase: CheckMovieToWatchUseCase,
                                               private val checkWatchedMovieUseCase: CheckWatchedMovieUseCase,
                                               private val deleteMovieToWatchByIdFromLocalDBUseCase: DeleteMovieToWatchByIdFromLocalDBUseCase,
                                               private val deleteWatchedMovieByIdFromLocalDBUseCase: DeleteWatchedMovieByIdFromLocalDBUseCase,
                                               private val getReminderDateAndTimeFromToWatchFromLocalDBUseCase: GetReminderDateAndTimeFromToWatchFromLocalDBUseCase): ViewModel() {

    var uiState by mutableStateOf(ItemDescriptionUiState())
        private set

    fun updateReminderDialogueStatus(status: Boolean) {
        uiState = uiState.copy(reminderDialogStatus = status)
    }

    fun changeDateToWatch(date: String?){
        uiState = uiState.copy(dateToWatch = date)
    }

    fun changeTimeToWatch(hours: Int?, minutes: Int?){
        uiState = uiState.copy(hoursToWatch = hours, minutesToWatch = minutes)
    }

    fun refreshCurrentMovieInfo(movieInfo: MovieInfo?) {
        uiState = uiState.copy(movieInfo = movieInfo)
    }
    fun refreshCurrentMovieInfoRoom(movieInfo: RoomMovieInfoForRetrofit?) {
        uiState = uiState.copy(roomMovieInfoForRetrofit = movieInfo)
    }

    fun checkMovieToWatch(movieId: Int, userId: Int){
        viewModelScope.launch {
            delay(250)
            kotlin.runCatching { checkMovieToWatchUseCase(movieId, userId) }
                .onSuccess { response ->
                    uiState = uiState.copy(movieToWatchStatus = response)
                    Napier.d("${uiState.movieToWatchStatus}", tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Не удалось получить статус фильма к просмотру: ${it.message}", tag = "DescriptionViewModel") }
        }
    }

    fun checkWatchedMovie(movieId: Int, userId: Int){
        viewModelScope.launch {
            delay(250)
            kotlin.runCatching { checkWatchedMovieUseCase(movieId, userId) }
                .onSuccess { response ->
                    uiState = uiState.copy(watchedMovieStatus = response)
                }
                .onFailure { Napier.e("Не удалось получить статус просмотренного фильма${it.message}", tag = "DescriptionViewModel") }
        }
    }

    fun getMovieInfo(id: Int){
        viewModelScope.launch {
            kotlin.runCatching { getMovieByIdUseCase(id) }
                .onSuccess { response ->
                    uiState = uiState.copy(movieInfo = response)
                }
                .onFailure { Napier.e("Данные не загрузились", tag = "DescriptionViewModel") }
        }
    }

    fun addMovieToDB(roomMovieInfo: RoomMovieInfoForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addMovieToDBUseCase(roomMovieInfo) }
                .onSuccess { response ->
                    Napier.d(response.toString(), tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Проблема при добавлении фильма в сеть: ${it.message}", tag = "DescriptionViewModel") }
        }
    }

    fun addMovieToWatchToDB(moviesToWatch: MoviesToWatchForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addMovieToWatchToDBUseCase(moviesToWatch) }
                .onSuccess { response ->
                    Napier.d(response.toString(), tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Проблема при добавлении фильма к просмотру: ${it.message}", tag = "DescriptionViewModel") }
        }
    }

    fun deleteMovieToWatchFromDB(moviesToWatch: MoviesToWatchForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { deleteMovieToWatchFromDBUseCase(moviesToWatch) }
                .onSuccess { response ->
                    Napier.d(response.toString(), tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Проблема при удалении фильма к просмотру: ${it.message}", tag = "DescriptionViewModel") }
        }
    }

    fun getReminderDateAndTime(id: Int){
        viewModelScope.launch {
            kotlin.runCatching { getReminderDateAndTimeFromToWatchFromLocalDBUseCase(id) }
                .onSuccess { response ->
                    uiState = uiState.copy(
                        dateToWatch = response.reminderDate,
                        hoursToWatch = response.reminderHour,
                        minutesToWatch = response.reminderMinute
                    )
                    Napier.d("Напоминание: ${response}", tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Напоминание не загрузилось", tag = "DescriptionViewModel") }
        }
    }

    fun addWatchedMovieToDB(watchedMovies: WatchedMoviesForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addWatchedMovieToDBUseCase(watchedMovies) }
                .onSuccess { response ->
                    Napier.d(response.toString(), tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Проблема при добавлении просмотренного филмьма: ${it.message}", tag = "DescriptionViewModel") }
        }
    }

    fun deleteWatchedMovieFromDB(watchedMovies: WatchedMoviesForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { deleteWatchedMovieFromDBUseCase(watchedMovies) }
                .onSuccess { response ->
                    Napier.d(response.toString(), tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Проблема при удалении просмотренного фильма: ${it.message}", tag = "DescriptionViewModel") }
        }
    }

    fun deleteWatchedMovieFromLocalDB(userId: Int, movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { deleteWatchedMovieByIdFromLocalDBUseCase(userId, movieId) }
                .onSuccess { response ->
                    Napier.d("Просмотренный фильм локально удалён", tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Проблема при удалении локального просмотренного фильма: ${it.message}", tag = "DescriptionViewModel") }
        }
    }

    fun deleteMovieToWatchFromLocalDB(userId: Int, movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { deleteMovieToWatchByIdFromLocalDBUseCase(userId, movieId) }
                .onSuccess { response ->
                    Napier.d("Фильм к просмотру локально удалён", tag = "DescriptionViewModel")
                }
                .onFailure { Napier.e("Проблема при удалении локального филмьа к просмотру: ${it.message}", tag = "DescriptionViewModel") }
        }
    }

}
