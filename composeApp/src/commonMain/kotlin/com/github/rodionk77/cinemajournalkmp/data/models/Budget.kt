package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Budget (

  @SerialName("value"    ) var value    : Int?    = null,
  @SerialName("currency" ) var currency : String? = null

)