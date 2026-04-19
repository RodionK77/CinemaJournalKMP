package com.example.cinemajournal.Domain.userUseCases

import com.github.rodionk77.cinemajournalkmp.data.UsersRepository
import com.github.rodionk77.cinemajournalkmp.data.models.SignUpRequest
import com.github.rodionk77.cinemajournalkmp.data.models.AuthResponse

class SignUpUserUseCase (private val repository: UsersRepository) {

    suspend operator fun invoke(request: SignUpRequest): AuthResponse  {
        return repository.signUpUser(request)
    }
}