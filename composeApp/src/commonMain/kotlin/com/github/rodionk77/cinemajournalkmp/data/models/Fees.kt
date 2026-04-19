package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Fees (

    @SerialName("world" ) var world : World? = World(),
    @SerialName("usa"   ) var usa   : Usa?   = Usa()

)