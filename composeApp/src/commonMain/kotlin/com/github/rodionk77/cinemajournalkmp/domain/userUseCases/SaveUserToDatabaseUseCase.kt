package com.example.cinemajournal.Domain.userUseCases

import com.github.rodionk77.cinemajournalkmp.data.UsersRepository
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User

class SaveUserToDatabaseUseCase (private val repository: UsersRepository) {

    suspend operator fun invoke(user: User)  {
        repository.saveUserToDatabase(user)
    }
}