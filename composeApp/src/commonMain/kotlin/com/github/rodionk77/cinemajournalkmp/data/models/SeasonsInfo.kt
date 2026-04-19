package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SeasonsInfo (

    @SerialName("number"        ) var number        : Int? = null,
    @SerialName("episodesCount" ) var episodesCount : Int? = null

)