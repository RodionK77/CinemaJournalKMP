package com.github.rodionk77.cinemajournalkmp.data

import com.github.rodionk77.cinemajournalkmp.data.api.Tokens
import com.github.rodionk77.cinemajournalkmp.data.models.MovieInfo
import com.github.rodionk77.cinemajournalkmp.data.models.MovieListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val PATH = "/v1.4/movie"

private val SELECT_FIELDS = listOf(
    "id", "name", "enName", "alternativeName", "description", "type", "typeNumber",
    "isSeries", "year", "releaseYears", "rating", "ageRating", "budget", "movieLength",
    "seriesLength", "genres", "countries", "poster", "persons", "fees", "premiere"
)

class MovieRepository(private val httpClient: HttpClient) {

    fun refreshSearchMovies(query: String) { }

    suspend fun getTop20(): MovieListResponse? {
        return httpClient.get(PATH) {
            SELECT_FIELDS.forEach { parameter("selectFields", it) }
            parameter("page", 1); parameter("limit", 20)
            parameter("type", "movie"); parameter("rating.kp", "8.0-10")
            parameter("votes.kp", "1000000-50000000"); parameter("lists", "top250")
            parameter("token", Tokens.TOKEN1)
        }.body()
    }

    suspend fun getTop20Kids(): MovieListResponse? {
        return httpClient.get(PATH) {
            SELECT_FIELDS.forEach { parameter("selectFields", it) }
            parameter("page", 1); parameter("limit", 20)
            parameter("type", "movie"); parameter("rating.kp", "8.0-10")
            parameter("ageRating", "0-12"); parameter("votes.kp", "500000-50000000")
            parameter("lists", "top250"); parameter("token", Tokens.TOKEN1)
        }.body()
    }

    suspend fun getTop20Interested(): MovieListResponse? {
        return httpClient.get(PATH) {
            SELECT_FIELDS.forEach { parameter("selectFields", it) }
            parameter("page", 1); parameter("limit", 20)
            parameter("type", "movie"); parameter("rating.kp", "8.0-10")
            parameter("token", Tokens.TOKEN1)
        }.body()
    }

    suspend fun getTop20InterestedKids(): MovieListResponse? {
        return httpClient.get(PATH) {
            SELECT_FIELDS.forEach { parameter("selectFields", it) }
            parameter("page", 1); parameter("limit", 20)
            parameter("type", "movie"); parameter("rating.kp", "8.0-10")
            parameter("ageRating", "0-12"); parameter("token", Tokens.TOKEN1)
        }.body()
    }

    suspend fun getTop20Series(): MovieListResponse? {
        return httpClient.get(PATH) {
            SELECT_FIELDS.forEach { parameter("selectFields", it) }
            parameter("page", 1); parameter("limit", 20)
            parameter("type", "tv-series"); parameter("type", "animated-series")
            parameter("rating.kp", "8.0-10"); parameter("votes.kp", "500000-50000000")
            parameter("token", Tokens.TOKEN1)
        }.body()
    }

    suspend fun getTop20KidsSeries(): MovieListResponse? {
        return httpClient.get(PATH) {
            SELECT_FIELDS.forEach { parameter("selectFields", it) }
            parameter("page", 1); parameter("limit", 20)
            parameter("type", "tv-series"); parameter("type", "animated-series")
            parameter("rating.kp", "8.0-10"); parameter("ageRating", "0-12")
            parameter("votes.kp", "100000-50000000"); parameter("token", Tokens.TOKEN1)
        }.body()
    }

    suspend fun getSearchMovies(query: String): MovieListResponse? {
        return httpClient.get("$PATH/search") {
            SELECT_FIELDS.forEach { parameter("selectFields", it) }
            parameter("page", 1); parameter("limit", 20)
            parameter("query", query); parameter("token", Tokens.TOKEN1)
        }.body()
    }

    suspend fun getFilteredMovies(
        type: List<String>?,
        year: String?,
        rating: String?,
        ageRating: String?,
        time: String?,
        genresName: List<String>?,
        countriesName: List<String>?
    ): MovieListResponse? {
        return httpClient.get(PATH) {
            SELECT_FIELDS.forEach { parameter("selectFields", it) }
            parameter("page", 1); parameter("limit", 20)
            type?.forEach { parameter("type", it) }
            if (!year.isNullOrEmpty()) parameter("year", year)
            if (!rating.isNullOrEmpty()) parameter("rating.kp", rating)
            if (!ageRating.isNullOrEmpty()) parameter("ageRating", ageRating)
            if (!time.isNullOrEmpty()) parameter("movieLength", time)
            genresName?.forEach { parameter("genres.name", it) }
            countriesName?.forEach { parameter("countries.name", it) }
            parameter("token", Tokens.TOKEN1)
        }.body()
    }

    suspend fun getMovieById(id: Int): MovieInfo? {
        return httpClient.get("$PATH/$id") {
            parameter("token", Tokens.TOKEN1)
        }.body()
    }
}
