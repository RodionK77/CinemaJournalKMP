package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest (
    var username: String,
    var email: String,
    var password: String,
    var role: Int
)