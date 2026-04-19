package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Rating (

  @SerialName("kp"                 ) var kp                 : Double? = null,
  @SerialName("imdb"               ) var imdb               : Double?    = null,
  @SerialName("filmCritics"        ) var filmCritics        : Double? = null,
  @SerialName("russianFilmCritics" ) var russianFilmCritics : Double?    = null,
  @SerialName("await"              ) var await              : Double? = null

)