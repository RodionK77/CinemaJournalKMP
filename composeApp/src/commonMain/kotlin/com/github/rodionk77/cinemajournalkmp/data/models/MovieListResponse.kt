package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieListResponse (

    @SerialName("docs"  ) var movieInfo  : ArrayList<MovieInfo> = arrayListOf(),
    @SerialName("total" ) var total : Int?            = null,
    @SerialName("limit" ) var limit : Int?            = null,
    @SerialName("page"  ) var page  : Int?            = null,
    @SerialName("pages" ) var pages : Int?            = null

)