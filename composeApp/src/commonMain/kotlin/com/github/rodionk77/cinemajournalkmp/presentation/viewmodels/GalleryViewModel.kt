package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.movieUseCases.GetFilteredMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetSearchMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20InterestedKidsMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20InterestedMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20KidsMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20KidsSeriesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20MoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.GetTop20SeriesUseCase
import com.example.cinemajournal.Domain.movieUseCases.RefreshMoviesUseCase
import com.example.cinemajournal.Domain.movieUseCases.RefreshSearchMoviesUseCase
import com.github.rodionk77.cinemajournalkmp.data.models.MovieInfo
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

data class ItemsCompilationUiState(
    val topMoviesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val topInterestedMoviesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val topSeriesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val userRole: Int = 0,
    val selectedMovieId: Int = 0,
    val isSearch: Boolean = false,
    val searchQuery: String = "",
    val isQueryGo: Boolean = false,
    val searchMoviesInfo  : ArrayList<MovieInfo> = arrayListOf(),
    val drawerState: Boolean = false,
    val genresList:MutableList<String> = mutableListOf(),
    val countriesList:MutableList<String> = mutableListOf(),
    val typesList:MutableList<String> = mutableListOf()
)

class GalleryViewModel (private val refreshMoviesUseCase: RefreshMoviesUseCase,
                                            private val getTop20MoviesUseCase: GetTop20MoviesUseCase,
                                            private val getTop20SeriesUseCase: GetTop20SeriesUseCase,
                                            private val getTop20KidsMoviesUseCase: GetTop20KidsMoviesUseCase,
                                            private val getTop20KidsSeriesUseCase: GetTop20KidsSeriesUseCase,
                                            private val getTop20InterestedMoviesUseCase: GetTop20InterestedMoviesUseCase,
                                            private val getTop20InterestedKidsMoviesUseCase: GetTop20InterestedKidsMoviesUseCase,
                                            private val refreshSearchMoviesUseCase: RefreshSearchMoviesUseCase,
                                            private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
                                            private val getFilteredMoviesUseCase: GetFilteredMoviesUseCase) : ViewModel(){

    var uiState by mutableStateOf(ItemsCompilationUiState())
        private set

    fun refreshTops(role: Int) {
        if (uiState.topMoviesInfo.isNotEmpty() && uiState.userRole == role) {
            Napier.d("refreshTops: пропускаем, данные уже загружены для role=$role", tag = "GalleryViewModel")
            return
        }
        uiState = uiState.copy(userRole = role)
        if (role == 0){
            viewModelScope.launch {
                kotlin.runCatching { getTop20MoviesUseCase() }
                    .onSuccess { uiState = uiState.copy(topMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Napier.e("Данные подборки 1 не загрузились: ${it.message}", tag = "GalleryViewModel") }
                kotlin.runCatching { getTop20InterestedMoviesUseCase() }
                    .onSuccess { uiState = uiState.copy(topInterestedMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Napier.e("Данные подборки 2 не загрузились: ${it.message}", tag = "GalleryViewModel") }
                kotlin.runCatching { getTop20SeriesUseCase() }
                    .onSuccess { uiState = uiState.copy(topSeriesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Napier.e("Данные подборки 3 не загрузились: ${it.message}", tag = "GalleryViewModel") }
            }
        } else {
            viewModelScope.launch {
                kotlin.runCatching { getTop20KidsMoviesUseCase() }
                    .onSuccess { uiState = uiState.copy(topMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Napier.e("Данные подборки 1 не загрузились: ${it.message}", tag = "GalleryViewModel") }
                kotlin.runCatching { getTop20InterestedKidsMoviesUseCase() }
                    .onSuccess { uiState = uiState.copy(topInterestedMoviesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Napier.e("Данные подборки 2 не загрузились: ${it.message}", tag = "GalleryViewModel") }
                kotlin.runCatching { getTop20KidsSeriesUseCase() }
                    .onSuccess { uiState = uiState.copy(topSeriesInfo = it?.movieInfo ?: arrayListOf()) }
                    .onFailure { Napier.e("Данные подборки 3 не загрузились: ${it.message}", tag = "GalleryViewModel") }
            }
        }
    }

    fun refreshSearchMovies(query: String){
        Napier.d("refreshSearchMovies вызван с query='$query'", tag = "GalleryViewModel")
        viewModelScope.launch {
            kotlin.runCatching { getSearchMoviesUseCase(query) }
                .onSuccess { response ->
                    Napier.d("refreshSearchMovies успех: ${response?.movieInfo?.size} фильмов", tag = "GalleryViewModel")
                    val sorted = response?.movieInfo?.sortedByDescending { it.rating?.kp ?: 0.0 }
                    uiState = uiState.copy(searchMoviesInfo = ArrayList(sorted ?: arrayListOf()))
                }
                .onFailure { Napier.e("Данные поиска не загрузились: ${it.message}\n${it.stackTraceToString()}", tag = "GalleryViewModel") }
        }
    }

    fun refreshFilteredMovies(type: List<String>?, year:String?, rating: String?, ageRating: String?, time: String?, genresName: List<String>?, countriesName: List<String>?){
        viewModelScope.launch {
            kotlin.runCatching { getFilteredMoviesUseCase(type = type, year = year, rating = rating, ageRating = ageRating, time = time, genresName = genresName, countriesName = countriesName) }
                .onSuccess { response ->
                    val sorted = response?.movieInfo?.sortedByDescending { it.rating?.kp ?: 0.0 }
                    uiState = uiState.copy(searchMoviesInfo = ArrayList(sorted ?: arrayListOf()))
                }
                .onFailure { Napier.e("Данные фильтров не загрузились: ${it.message}", tag = "GalleryViewModel") }
        }
    }

    fun selectMovie(id: Int){ uiState = uiState.copy(selectedMovieId = id) }
    fun changeSearchState(b: Boolean){ uiState = uiState.copy(isSearch = b) }
    fun changeSearchQuery(s: String){ uiState = uiState.copy(searchQuery = s) }
    fun changeQueryGo(b: Boolean){ uiState = uiState.copy(isQueryGo = b) }
    fun changeDrawerState(b: Boolean){ uiState = uiState.copy(drawerState = b) }

    fun addGenre(s: String){ uiState = uiState.copy(genresList = (uiState.genresList + s).toMutableList()) }
    fun removeGenre(s: String){ uiState = uiState.copy(genresList = uiState.genresList.filter { it != s }.toMutableList()) }
    fun removeAllGenres(){ uiState = uiState.copy(genresList = mutableListOf()) }

    fun addCountry(s: String){ uiState = uiState.copy(countriesList = (uiState.countriesList + s).toMutableList()) }
    fun removeAllCountries(){ uiState = uiState.copy(countriesList = mutableListOf()) }
    fun removeCountry(s: String){ uiState = uiState.copy(countriesList = uiState.countriesList.filter { it != s }.toMutableList()) }
}
