package com.github.rodionk77.cinemajournalkmp.data

import com.github.rodionk77.cinemajournalkmp.data.database.UsersDAO
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Countries
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.CountriesForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Dislikes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Genres
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.GenresForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Likes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatch
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatchForRetrofit
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
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMoviesForRetrofit
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

private const val PATH = "/api/movies"

class MoviesDBRepository(private val dao: UsersDAO, private val httpClient: HttpClient) {

    suspend fun getUser(id: Int): UserForRetrofit? =
        httpClient.get("$PATH/getUser/$id").body()

    suspend fun getMovie(id: Int): RoomMovieInfoForRetrofit? =
        httpClient.get("$PATH/getMovie/$id").body()

    suspend fun addMovieToDB(requestBody: RoomMovieInfoForRetrofit): RoomMovieInfoForRetrofit =
        httpClient.post("$PATH/addMovie") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()

    suspend fun addMovieToWatchToDB(requestBody: MoviesToWatchForRetrofit): MoviesToWatchForRetrofit =
        httpClient.post("$PATH/addMovieToWatch") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()

    suspend fun deleteMovieToWatchFromDB(requestBody: MoviesToWatchForRetrofit) {
        httpClient.delete("$PATH/deleteMovietoWatch") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    suspend fun updateMovieToWatchToDB(requestBody: MoviesToWatchForRetrofit) {
        httpClient.put("$PATH/updateMovieToWatch") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    suspend fun addWatchedMovieToDB(requestBody: WatchedMoviesForRetrofit): WatchedMoviesForRetrofit? =
        httpClient.post("$PATH/addWatchedMovie") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()

    suspend fun deleteWatchedMovieFromDB(requestBody: WatchedMoviesForRetrofit) {
        httpClient.delete("$PATH/deleteWatchedMovie") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    suspend fun updateWatchedMovieToDB(requestBody: WatchedMoviesForRetrofit) {
        httpClient.put("$PATH/updateWatchedMovie") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    suspend fun addReviewToDB(requestBody: ReviewForRetrofit): ReviewForRetrofit? =
        httpClient.post("$PATH/addReview") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }.body()

    suspend fun updateReviewToDB(requestBody: Review) {
        httpClient.put("$PATH/updateReview") {
            contentType(ContentType.Application.Json)
            setBody(requestBody)
        }
    }

    // Local DB

    suspend fun saveMovieToWatchToLocalDB(item: MoviesToWatch) = dao.saveMovieToWatchToLocalDB(item)
    suspend fun getAllMoviesToWatchByIdFromLocalDB(item: User): List<MoviesToWatch>? = dao.getAllMoviesToWatchById(item.id)
    suspend fun checkMovieToWatch(movieId: Int, userId: Int): Boolean = dao.checkMovieToWatch(movieId, userId)
    suspend fun deleteMovieToWatchByIdFromLocalDB(userId: Int, movieId: Int) = dao.deleteMoviesToWatchById(userId, movieId)
    suspend fun deleteAllMoviesToWatchFromLocalDB() = dao.deleteAllMoviesToWatchFromLocalDB()
    suspend fun getReminderDateByIdFromToWatchFromLocalDB(movieId: Int): String? = dao.getReminderDateByIdFromToWatchFromLocalDB(movieId)
    suspend fun getReminderHourByIdFromToWatchFromLocalDB(movieId: Int): Int? = dao.getReminderHourByIdFromToWatchFromLocalDB(movieId)
    suspend fun getReminderMinuteByIdFromToWatchFromLocalDB(movieId: Int): Int? = dao.getReminderMinuteByIdFromToWatchFromLocalDB(movieId)

    suspend fun saveWatchedMovieToLocalDB(item: WatchedMovies) = dao.saveWatchedMovieToLocalDB(item)
    suspend fun getAllWatchedMoviesByIdFromLocalDB(item: User): List<WatchedMovies>? = dao.getAllWatchedMoviesById(item.id)
    suspend fun checkWatchedMovie(movieId: Int, userId: Int): Boolean = dao.checkWatchedMovie(movieId, userId)
    suspend fun deleteWatchedMovieByIdFromLocalDB(userId: Int, movieId: Int) = dao.deleteWatchedMoviesById(userId, movieId)
    suspend fun deleteAllWatchedMoviesFromLocalDB() = dao.deleteAllWatchedMoviesFromLocalDB()

    suspend fun getMoviesFromToWatchFromLocalDB(): List<RoomMovieInfo>? = dao.getMoviesFromToWatchFromLocalDB()
    suspend fun getMoviesFromWatchedFromLocalDB(): List<RoomMovieInfo>? = dao.getMoviesFromWatchedFromLocalDB()

    suspend fun saveReviewToLocalDB(item: Review) = dao.saveReviewToLocalDB(item)
    suspend fun getAllReviewsByIdFromLocalDB(item: User): List<Review>? = dao.getAllReviewsById(item.id)
    suspend fun deleteReviewFromLocalDB(item: Review) = dao.deleteReviewById(item.userId, item.movieId)
    suspend fun deleteAllReviewsFromLocalDB() = dao.deleteAllReviewsFromLocalDB()

    suspend fun saveLikesToLocalDB(item: Likes) = dao.saveLikesToLocalDB(item)
    suspend fun getAllLikesByIdFromLocalDB(item: User): List<Likes>? = dao.getAllLikesById(item.id)
    suspend fun getLikesByIdFromLocalDB(movieId: Int): List<Likes> = dao.getLikesByIdFromLocalDB(movieId)
    suspend fun deleteLikesByIdFromLocalDB(movieId: Int) = dao.deleteLikesByIdFromLocalDB(movieId)
    suspend fun deleteAllLikesFromLocalDB() = dao.deleteAllLikesFromLocalDB()

    suspend fun saveDislikesToLocalDB(item: Dislikes) = dao.saveDislikesToLocalDB(item)
    suspend fun getAllDislikesByIdFromLocalDB(item: User): List<Dislikes>? = dao.getAllDislikesById(item.id)
    suspend fun getDislikesByIdFromLocalDB(movieId: Int): List<Dislikes> = dao.getDislikesByIdFromLocalDB(movieId)
    suspend fun deleteDislikesByIdFromLocalDB(movieId: Int) = dao.deleteDislikesByIdFromLocalDB(movieId)
    suspend fun deleteAllDislikesFromLocalDB() = dao.deleteAllDislikesFromLocalDB()

    suspend fun saveMovieToLocalDB(item: RoomMovieInfo) = dao.saveMovieToLocalDB(item)
    suspend fun getMovieByIdFromLocalDB(id: Int): RoomMovieInfo? = dao.getMovieByIdFromLocalDB(id)
    suspend fun getAllMoviesFromLocalDB(): List<RoomMovieInfo>? = dao.getAllMoviesFromLocalDB()
    suspend fun deleteMovieByIdFromLocalDB(id: Int) = dao.deleteMovieByIdFromLocalDB(id)
    suspend fun deleteAllMoviesFromLocalDB() = dao.deleteAllMoviesFromLocalDB()

    suspend fun saveCountriesToLocalDB(item: Countries) = dao.saveCountriesToLocalDB(item)
    suspend fun getCountriesByIdFromLocalDB(movieId: Int): List<CountriesForRetrofit> = dao.getCountriesByIdFromLocalDB(movieId)
    suspend fun saveGenresToLocalDB(item: Genres) = dao.saveGenresToLocalDB(item)
    suspend fun getGenresByIdFromLocalDB(movieId: Int): List<GenresForRetrofit> = dao.getGenresByIdFromLocalDB(movieId)
    suspend fun savePersonsToLocalDB(item: Persons) = dao.savePersonsToLocalDB(item)
    suspend fun getPersonsByIdFromLocalDB(movieId: Int): List<PersonsForRetrofit> = dao.getPersonsByIdFromLocalDB(movieId)
    suspend fun getReviewByIdFromLocalDB(movieId: Int): Review = dao.getReviewByIdFromLocalDB(movieId)
    suspend fun saveSeasonsInfoToLocalDB(item: SeasonsInfo) = dao.saveSeasonsInfoToLocalDB(item)
    suspend fun getSeasonsInfoByIdFromLocalDB(movieId: Int): List<SeasonsInfoForRetrofit> = dao.getSeasonsInfoByIdFromLocalDB(movieId)
}
