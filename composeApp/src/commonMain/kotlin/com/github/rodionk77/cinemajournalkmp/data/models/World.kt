package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class World (

  @SerialName("value"    ) var value    : Long?    = null,
  @SerialName("currency" ) var currency : String? = null

)