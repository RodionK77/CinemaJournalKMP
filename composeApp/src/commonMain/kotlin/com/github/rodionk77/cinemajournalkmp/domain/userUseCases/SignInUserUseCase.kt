package com.example.cinemajournal.Domain.userUseCases

import com.github.rodionk77.cinemajournalkmp.data.UsersRepository
import com.github.rodionk77.cinemajournalkmp.data.models.AuthResponse
import com.github.rodionk77.cinemajournalkmp.data.models.SignInRequest

class SignInUserUseCase (private val repository: UsersRepository) {

    suspend operator fun invoke(request: SignInRequest): AuthResponse {
        return repository.signInUser(request)
    }
}