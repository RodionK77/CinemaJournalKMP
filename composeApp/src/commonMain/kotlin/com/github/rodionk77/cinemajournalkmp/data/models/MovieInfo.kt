package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieInfo (

    @SerialName("fees"            ) var fees            : Fees?                = Fees(),
    @SerialName("rating"          ) var rating          : Rating?              = Rating(),
    @SerialName("movieLength"     ) var movieLength     : Int?                 = null,
    @SerialName("id"              ) var id              : Int?                 = null,
    @SerialName("type"            ) var type            : String?              = null,
    @SerialName("typeNumber"      ) var typeNumber      : Int?                 = null,
    @SerialName("name"            ) var name            : String?              = null,
    @SerialName("description"     ) var description     : String?              = null,
    @SerialName("premiere"        ) var premiere        : Premiere?            = Premiere(),
    @SerialName("year"            ) var year            : Int?                 = null,
    @SerialName("budget"          ) var budget          : Budget?              = Budget(),
    @SerialName("poster"          ) var poster          : Poster?              = Poster(),
    @SerialName("genres"          ) var genres          : ArrayList<Genres>    = arrayListOf(),
    @SerialName("countries"       ) var countries       : ArrayList<Countries> = arrayListOf(),
    @SerialName("persons"         ) var persons         : ArrayList<Persons>   = arrayListOf(),
    @SerialName("alternativeName" ) var alternativeName : String?              = null,
    @SerialName("enName"          ) var enName          : String?              = null,
    @SerialName("ageRating"       ) var ageRating       : Int?                 = null,
    @SerialName("isSeries"        ) var isSeries        : Boolean?             = null,
    @SerialName("seriesLength"    ) var seriesLength    : Int?              = null,
    @SerialName("totalSeriesLength") var totalSeriesLength   : Int?            = null,
    @SerialName("seasonsInfo"     ) var seasonsInfo     : ArrayList<SeasonsInfo> = arrayListOf(),

    )