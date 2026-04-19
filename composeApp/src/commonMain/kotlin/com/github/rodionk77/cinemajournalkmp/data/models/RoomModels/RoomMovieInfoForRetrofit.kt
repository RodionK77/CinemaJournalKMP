package com.github.rodionk77.cinemajournalkmp.data.models.RoomModels

import kotlinx.serialization.Serializable

@Serializable
data class RoomMovieInfoForRetrofit (
    var id: Int? = null,
    var name: String? = null,
    var feesWorld: Long? = null,
    var feesUsa: Long? = null,
    var budget: Int? = null,
    var posterUrl: String? = null,
    var worldPremier: String? = null,
    var russiaPremier: String? = null,
    var kpRating: Double? = null,
    var imdbRating: Double? = null,
    var movieLength: Int = 0,
    var type: String? = null,
    var typeNumber: Int = 0,
    var description: String? = null,
    var year: Int = 0,
    var alternativeName: String? = null,
    var enName: String? = null,
    var ageRating: String? = null,
    var isSeries: Boolean? = null,
    var seriesLength: String? = null,
    var totalSeriesLength: String? = null,
    var countries: List<CountriesForRetrofit>? = null,
    var genres: List<GenresForRetrofit>? = null,
    var persons: List<PersonsForRetrofit>? = null,
    var seasonsInfo: List<SeasonsInfoForRetrofit>? = null,
    var reviews: ReviewForRetrofit? = null,
    var countriesStr: String? = null,
    var genresStr: String? = null,
    )