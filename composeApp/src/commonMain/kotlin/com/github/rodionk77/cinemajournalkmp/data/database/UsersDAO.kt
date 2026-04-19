package com.github.rodionk77.cinemajournalkmp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMovies

@Dao
interface UsersDAO {

    @Query("DELETE FROM User WHERE id = :userId")
    suspend fun deleteUserById(userId: Int)

    @Query("DELETE FROM MoviesToWatch WHERE user_id = :userId AND movie_id = :movieId")
    suspend fun deleteMoviesToWatchById(userId: Int, movieId: Int)

    @Query("SELECT * FROM User")
    suspend fun getAllUsers(): List<User>

    @Insert(entity = User::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(item: User)

    @Insert(entity = RoomMovieInfo::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieToLocalDB(item: RoomMovieInfo)

    @Query("SELECT * FROM RoomMovieInfo WHERE id = :movieId")
    suspend fun getMovieByIdFromLocalDB(movieId: Int): RoomMovieInfo?

    @Query("SELECT * FROM RoomMovieInfo")
    suspend fun getAllMoviesFromLocalDB(): List<RoomMovieInfo>

    @Query("DELETE FROM RoomMovieInfo WHERE id = :movieId")
    suspend fun deleteMovieByIdFromLocalDB(movieId: Int)

    @Query("DELETE FROM RoomMovieInfo")
    suspend fun deleteAllMoviesFromLocalDB()

    @Insert(entity = Countries::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountriesToLocalDB(item: Countries)

    @Query("SELECT * FROM Countries WHERE content_id = :movieId")
    suspend fun getCountriesByIdFromLocalDB(movieId: Int): List<CountriesForRetrofit>

    @Insert(entity = Genres::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenresToLocalDB(item: Genres)

    @Query("SELECT * FROM Genres WHERE content_id = :movieId")
    suspend fun getGenresByIdFromLocalDB(movieId: Int): List<GenresForRetrofit>

    @Insert(entity = Persons::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePersonsToLocalDB(item: Persons)

    @Query("SELECT * FROM Persons WHERE content_id = :movieId")
    suspend fun getPersonsByIdFromLocalDB(movieId: Int): List<PersonsForRetrofit>

    @Insert(entity = SeasonsInfo::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSeasonsInfoToLocalDB(item: SeasonsInfo)

    @Query("SELECT * FROM SeasonsInfo WHERE content_id = :movieId")
    suspend fun getSeasonsInfoByIdFromLocalDB(movieId: Int): List<SeasonsInfoForRetrofit>

    @Query("SELECT * FROM RoomMovieInfo WHERE id IN (SELECT movie_id FROM MoviesToWatch)")
    suspend fun getMoviesFromToWatchFromLocalDB(): List<RoomMovieInfo>?

    @Query("SELECT * FROM RoomMovieInfo WHERE id IN (SELECT movie_id FROM WatchedMovies)")
    suspend fun getMoviesFromWatchedFromLocalDB(): List<RoomMovieInfo>?

    @Insert(entity = MoviesToWatch::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovieToWatchToLocalDB(item: MoviesToWatch)

    @Query("SELECT * FROM MoviesToWatch WHERE user_id = :userId")
    suspend fun getAllMoviesToWatchById(userId: Int): List<MoviesToWatch>

    @Query("SELECT EXISTS (SELECT 1 FROM MoviesToWatch WHERE movie_id = :movieId AND user_id = :userId)")
    suspend fun checkMovieToWatch(movieId: Int, userId: Int): Boolean

    @Query("DELETE FROM MoviesToWatch")
    suspend fun deleteAllMoviesToWatchFromLocalDB()

    @Query("SELECT reminderDate FROM MoviesToWatch WHERE movie_id = :movieId")
    suspend fun getReminderDateByIdFromToWatchFromLocalDB(movieId: Int): String?

    @Query("SELECT reminderHour FROM MoviesToWatch WHERE movie_id = :movieId")
    suspend fun getReminderHourByIdFromToWatchFromLocalDB(movieId: Int): Int?

    @Query("SELECT reminderMinute FROM MoviesToWatch WHERE movie_id = :movieId")
    suspend fun getReminderMinuteByIdFromToWatchFromLocalDB(movieId: Int): Int?

    @Insert(entity = WatchedMovies::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWatchedMovieToLocalDB(item: WatchedMovies)

    @Query("SELECT * FROM WatchedMovies WHERE user_id = :userId")
    suspend fun getAllWatchedMoviesById(userId: Int): List<WatchedMovies>

    @Query("DELETE FROM WatchedMovies WHERE user_id = :userId AND movie_id = :movieId")
    suspend fun deleteWatchedMoviesById(userId: Int, movieId: Int)

    @Query("SELECT EXISTS (SELECT 1 FROM WatchedMovies WHERE movie_id = :movieId AND user_id = :userId)")
    suspend fun checkWatchedMovie(movieId: Int, userId: Int): Boolean

    @Query("DELETE FROM WatchedMovies")
    suspend fun deleteAllWatchedMoviesFromLocalDB()

    @Insert(entity = Review::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveReviewToLocalDB(item: Review)

    @Query("SELECT * FROM Review WHERE user_id = :userId")
    suspend fun getAllReviewsById(userId: Int): List<Review>

    @Query("SELECT * FROM Review WHERE movie_id = :movieId")
    suspend fun getReviewByIdFromLocalDB(movieId: Int): Review

    @Query("DELETE FROM Review WHERE user_id = :userId AND movie_id = :movieId")
    suspend fun deleteReviewById(userId: Int, movieId: Int)

    @Query("DELETE FROM Review")
    suspend fun deleteAllReviewsFromLocalDB()

    @Insert(entity = Likes::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLikesToLocalDB(item: Likes)

    @Query("SELECT * FROM Likes WHERE user_id = :userId")
    suspend fun getAllLikesById(userId: Int): List<Likes>

    @Query("SELECT * FROM Likes WHERE movie_id = :movieId")
    suspend fun getLikesByIdFromLocalDB(movieId: Int): List<Likes>

    @Query("DELETE FROM Likes WHERE movie_id = :movieId")
    suspend fun deleteLikesByIdFromLocalDB(movieId: Int)

    @Query("DELETE FROM Likes")
    suspend fun deleteAllLikesFromLocalDB()

    @Insert(entity = Dislikes::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDislikesToLocalDB(item: Dislikes)

    @Query("SELECT * FROM Dislikes WHERE user_id = :userId")
    suspend fun getAllDislikesById(userId: Int): List<Dislikes>

    @Query("SELECT * FROM Dislikes WHERE movie_id = :movieId")
    suspend fun getDislikesByIdFromLocalDB(movieId: Int): List<Dislikes>

    @Query("DELETE FROM Dislikes WHERE movie_id = :movieId")
    suspend fun deleteDislikesByIdFromLocalDB(movieId: Int)

    @Query("DELETE FROM Dislikes")
    suspend fun deleteAllDislikesFromLocalDB()

}