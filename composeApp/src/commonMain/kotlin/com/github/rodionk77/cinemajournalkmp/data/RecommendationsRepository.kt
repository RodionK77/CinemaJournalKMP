package com.github.rodionk77.cinemajournalkmp.data

import com.github.rodionk77.cinemajournalkmp.data.models.RecommendationErrorResponse
import com.github.rodionk77.cinemajournalkmp.data.models.RecommendationResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

private const val BASE_PATH = "/api/recommendations"

class RecommendationsRepository(private val httpClient: HttpClient) {

    suspend fun generateRecommendations(userId: Long): RecommendationResponse {
        val response: HttpResponse = httpClient.post("$BASE_PATH/generate") {
            parameter("userId", userId)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body()
            HttpStatusCode.BadRequest -> {
                val errorBody = kotlin.runCatching { response.body<RecommendationErrorResponse>() }.getOrNull()
                throw Exception(errorBody?.error ?: "Недостаточно данных для генерации рекомендаций")
            }
            else -> {
                val errorBody = kotlin.runCatching { response.body<RecommendationErrorResponse>() }.getOrNull()
                throw Exception(errorBody?.error ?: "Ошибка AI сервиса (${response.status.value})")
            }
        }
    }

    suspend fun getLatestRecommendations(userId: Long): RecommendationResponse? {
        val response: HttpResponse = httpClient.get("$BASE_PATH/latest") {
            parameter("userId", userId)
        }
        return when (response.status) {
            HttpStatusCode.OK -> response.body()
            HttpStatusCode.NotFound -> null
            else -> {
                val errorBody = kotlin.runCatching { response.body<RecommendationErrorResponse>() }.getOrNull()
                throw Exception(errorBody?.error ?: "Ошибка загрузки рекомендаций (${response.status.value})")
            }
        }
    }
}
