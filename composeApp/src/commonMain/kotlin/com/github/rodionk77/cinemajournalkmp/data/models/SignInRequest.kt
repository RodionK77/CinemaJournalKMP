package com.github.rodionk77.cinemajournalkmp.data.models

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest (
    var username: String,
    var password: String,
)