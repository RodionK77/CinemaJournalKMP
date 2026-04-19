package com.example.cinemajournal.Domain.userUseCases

import com.github.rodionk77.cinemajournalkmp.data.UsersRepository
import com.github.rodionk77.cinemajournalkmp.data.models.AuthResponse

class SignOutUserUseCase (private val repository: UsersRepository) {

    suspend operator fun invoke(): AuthResponse {
        return repository.signOutUser()
    }
}