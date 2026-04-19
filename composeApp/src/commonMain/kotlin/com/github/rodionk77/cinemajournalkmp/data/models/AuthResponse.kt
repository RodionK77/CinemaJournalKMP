package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse (

    @SerialName("message" ) var message : String? = null,
    var id: Int? = null,
    var username: String? = null,
    var email: String? = null,
    var role: Int? = null
) {
}