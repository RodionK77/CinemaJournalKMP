package com.example.cinemajournal.Domain.userUseCases

import com.github.rodionk77.cinemajournalkmp.data.UsersRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User

class GetAllUsersUseCase (private val repository: UsersRepository) {

    suspend operator fun invoke(): List<User>  {
        return repository.getAllUsers()
    }
}