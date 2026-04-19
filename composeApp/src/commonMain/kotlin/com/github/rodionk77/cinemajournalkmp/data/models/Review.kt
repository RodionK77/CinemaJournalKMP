package com.github.rodionk77.cinemajournalkmp.data.models

data class Review(
    var movieInfo: MovieInfo,
    val rating: Int = 0,
    var likes: ArrayList<String> = arrayListOf(),
    var dislikes: ArrayList<String> = arrayListOf(),
    var notes: String = ""
)
