package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull

@Serializable
data class RecommendationItemResponse(
    @SerialName("movieId"  ) val movieId  : Int    = 0,
    @SerialName("position" ) val position : Int    = 0,
    @SerialName("reason"   ) val reason   : String = ""
)

@Serializable
data class RecommendationResponse(
    @SerialName("recommendationId" ) val recommendationId : Long                             = 0L,
    @SerialName("status"           ) val status           : String                           = "",
    @SerialName("createdAt"        ) val createdAt        : JsonElement                      = JsonNull,
    @SerialName("items"            ) val items            : List<RecommendationItemResponse> = emptyList()
)

@Serializable
data class RecommendationErrorResponse(
    @SerialName("error"  ) val error  : String? = null,
    @SerialName("status" ) val status : String? = null
)
