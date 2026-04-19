package com.example.cinemajournal.Domain.moviesDBUseCases

import com.github.rodionk77.cinemajournalkmp.data.MoviesDBRepository
import com.github.rodionk77.cinemajournalkmp.data.models.ReminderModel

class GetReminderDateAndTimeFromToWatchFromLocalDBUseCase (private val repository: MoviesDBRepository) {

    suspend operator fun invoke(movieId: Int): ReminderModel {
        return ReminderModel(
            reminderDate = repository.getReminderDateByIdFromToWatchFromLocalDB(movieId),
            reminderHour = repository.getReminderHourByIdFromToWatchFromLocalDB(movieId),
            reminderMinute = repository.getReminderMinuteByIdFromToWatchFromLocalDB(movieId)
        )
    }
}