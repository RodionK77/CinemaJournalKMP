package com.example.cinemajournal.Domain.userUseCases

import com.github.rodionk77.cinemajournalkmp.data.UsersRepository

class DeleteUserByIdUseCase (private val repository: UsersRepository) {

    suspend operator fun invoke(id: Int)  {
        repository.deleteUserById(id)
    }
}