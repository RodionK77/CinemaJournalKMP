package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Persons (

  @SerialName("id"           ) var id           : Int?    = null,
  @SerialName("photo"        ) var photo        : String? = null,
  @SerialName("name"         ) var name         : String? = null,
  @SerialName("enName"       ) var enName       : String? = null,
  @SerialName("description"  ) var description  : String? = null,
  @SerialName("profession"   ) var profession   : String? = null,
  @SerialName("enProfession" ) var enProfession : String? = null

)