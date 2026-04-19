package com.example.cinemajournal.ui.theme.screens.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemajournal.Domain.moviesDBUseCases.AddReviewToDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteDislikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.DeleteLikesByIdFromLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveDislikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveLikesToLocalDBUseCase
import com.example.cinemajournal.Domain.moviesDBUseCases.SaveReviewToLocalDBUseCase
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Dislikes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Likes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Review
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.ReviewForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import io.github.aakira.napier.Napier
import kotlinx.coroutines.launch

data class ItemReviewUiState(
    val roomMovieInfoForRetrofit: RoomMovieInfoForRetrofit? = null,
    val user: User? = null,
    val likesForReview: MutableList<Likes>? = null,
    val dislikesForReview: MutableList<Dislikes>? = null,
    val rating: Float? = null,
    val reviewText: String? = null,
    val dateWatched: String? = null
)

class ReviewViewModel (private val addReviewToDBUseCase: AddReviewToDBUseCase,
                                          private val saveReviewToLocalDBUseCase: SaveReviewToLocalDBUseCase,
                                          private val saveLikesToLocalDBUseCase: SaveLikesToLocalDBUseCase,
                                          private val saveDislikesToLocalDBUseCase: SaveDislikesToLocalDBUseCase,
                                          private val deleteLikesByIdFromLocalDBUseCase: DeleteLikesByIdFromLocalDBUseCase,
                                          private val deleteDislikesByIdFromLocalDBUseCase: DeleteDislikesByIdFromLocalDBUseCase
) : ViewModel() {

    var uiState by mutableStateOf(ItemReviewUiState())
        private set

    fun refreshCurrentMovieInfoRoom(movieInfo: RoomMovieInfoForRetrofit?) {
        uiState = uiState.copy(roomMovieInfoForRetrofit = movieInfo)
    }

    fun changeRating(rating: Float?){ uiState = uiState.copy(rating = rating) }
    fun changeReviewText(reviewText: String?){ uiState = uiState.copy(reviewText = reviewText) }
    fun changeDate(date: String?){ uiState = uiState.copy(dateWatched = date) }
    fun changeLikes(likes: MutableList<Likes>?){ uiState = uiState.copy(likesForReview = likes) }
    fun addLike(like: Likes){ uiState = uiState.copy(likesForReview = ((uiState.likesForReview ?: mutableListOf()) + like).toMutableList()) }
    fun changeDislikes(dislikes: MutableList<Dislikes>?){ uiState = uiState.copy(dislikesForReview = dislikes) }
    fun addDislike(dislike: Dislikes){ uiState = uiState.copy(dislikesForReview = ((uiState.dislikesForReview ?: mutableListOf()) + dislike).toMutableList()) }

    fun addReviewToDB(review: ReviewForRetrofit){
        viewModelScope.launch {
            kotlin.runCatching { addReviewToDBUseCase(review) }
                .onSuccess { Napier.d("Ревью добавлено в удалённую бд", tag = "ReviewViewModel") }
                .onFailure { Napier.e("Проблема при добавлении ревью в удалённую бд: ${it.message}", tag = "ReviewViewModel") }
        }
    }

    fun saveReviewToLocalDB(review: Review){
        viewModelScope.launch {
            kotlin.runCatching { saveReviewToLocalDBUseCase(review) }
                .onSuccess { Napier.d("Обновленное ревью записано", tag = "ReviewViewModel") }
                .onFailure { Napier.e("Обновленное ревью не записано: ${it.message}", tag = "ReviewViewModel") }
        }
    }

    fun saveLikesToLocalDB(likes: Likes){
        viewModelScope.launch {
            kotlin.runCatching { saveLikesToLocalDBUseCase(likes) }
                .onSuccess { Napier.d("Обновленные лайки записаны", tag = "ReviewViewModel") }
                .onFailure { Napier.e("Обновленные лайки не записаны: ${it.message}", tag = "ReviewViewModel") }
        }
    }

    fun saveDislikesToLocalDB(dislikes: Dislikes){
        viewModelScope.launch {
            kotlin.runCatching { saveDislikesToLocalDBUseCase(dislikes) }
                .onSuccess { Napier.d("Обновленные дизлайки записаны", tag = "ReviewViewModel") }
                .onFailure { Napier.e("Обновленные дизлайки не записаны: ${it.message}", tag = "ReviewViewModel") }
        }
    }

    fun deleteLikesFromLocalDB(movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { deleteLikesByIdFromLocalDBUseCase(movieId) }
                .onSuccess { Napier.d("Лайки для фильма удалены", tag = "ReviewViewModel") }
                .onFailure { Napier.e("Проблема при удалении лайков для фильма: ${it.message}", tag = "ReviewViewModel") }
        }
    }

    fun deleteDislikesFromLocalDB(movieId: Int){
        viewModelScope.launch {
            kotlin.runCatching { deleteDislikesByIdFromLocalDBUseCase(movieId) }
                .onSuccess { Napier.d("Дизлайки для фильма удалены", tag = "ReviewViewModel") }
                .onFailure { Napier.e("Проблема при удалении дизлайков для фильма: ${it.message}", tag = "ReviewViewModel") }
        }
    }
}
