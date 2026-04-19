package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Premiere (

  @SerialName("world"  ) var world  : String? = null,
  @SerialName("russia" ) var russia : String? = null

)