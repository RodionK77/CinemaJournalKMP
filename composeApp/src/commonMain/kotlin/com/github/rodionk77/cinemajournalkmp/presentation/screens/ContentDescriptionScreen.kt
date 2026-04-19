package com.github.rodionk77.cinemajournalkmp.presentation.screens

import io.github.aakira.napier.Napier
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.accept
import cinemajournalkmp.composeapp.generated.resources.actors
import cinemajournalkmp.composeapp.generated.resources.age
import cinemajournalkmp.composeapp.generated.resources.budget
import cinemajournalkmp.composeapp.generated.resources.cancel
import cinemajournalkmp.composeapp.generated.resources.directors
import cinemajournalkmp.composeapp.generated.resources.fees_world
import cinemajournalkmp.composeapp.generated.resources.genre
import cinemajournalkmp.composeapp.generated.resources.kp_rating_short
import cinemajournalkmp.composeapp.generated.resources.loading
import cinemajournalkmp.composeapp.generated.resources.poster_not_loaded
import cinemajournalkmp.composeapp.generated.resources.poster_placeholder
import cinemajournalkmp.composeapp.generated.resources.remind
import cinemajournalkmp.composeapp.generated.resources.seasons_and_episodes
import cinemajournalkmp.composeapp.generated.resources.select_a_date
import cinemajournalkmp.composeapp.generated.resources.select_a_time
import cinemajournalkmp.composeapp.generated.resources.set_a_reminder
import cinemajournalkmp.composeapp.generated.resources.time
import cinemajournalkmp.composeapp.generated.resources.time_to_watch
import cinemajournalkmp.composeapp.generated.resources.year
import coil3.compose.AsyncImage
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.github.rodionk77.cinemajournalkmp.data.models.Persons
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatch
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatchForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.PersonsForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.SeasonsInfo
import com.github.rodionk77.cinemajournalkmp.presentation.items.photoItemRow
import com.github.rodionk77.cinemajournalkmp.presentation.items.seriesItemRow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentDescriptionScreen(
    navController: NavController,
    descriptionViewModel: DescriptionViewModel,
    authViewModel: AuthViewModel,
    journalsViewModel: JournalsViewModel,
) {
    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val timePickerState = rememberTimePickerState(is24Hour = true)

    val movieInfo = descriptionViewModel.uiState.movieInfo
    LaunchedEffect(movieInfo?.id) {
        val info = movieInfo ?: return@LaunchedEffect
        if (info.persons.isNotEmpty()) {
            journalsViewModel.writePersonsToLocalDB(info.persons, info.id!!)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(start = 16.dp, end = 16.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Napier.d("got: ${descriptionViewModel.uiState.movieInfo}", tag = "ContentDescriptionScreen")
        if (descriptionViewModel.uiState.movieInfo != null || descriptionViewModel.uiState.roomMovieInfoForRetrofit != null) {
            if (descriptionViewModel.uiState.dateToWatch == null) {
                descriptionViewModel.getReminderDateAndTime(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id ?: 0)
            }
            Content(descriptionViewModel)
        } else {
            Text(
                text = stringResource(Res.string.loading),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 16.sp
            )
        }

        if (descriptionViewModel.uiState.reminderDialogStatus) {
            reminderDialogue(
                descriptionViewModel = descriptionViewModel,
                authViewModel = authViewModel,
                journalsViewModel = journalsViewModel,
                onDateClick = { showDatePicker = true },
                onTimeClick = { showTimePicker = true }
            )
        }

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            descriptionViewModel.changeDateToWatch(epochMillisToDateString(millis))
                        }
                        showDatePicker = false
                    }) {
                        Text(stringResource(Res.string.accept))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text(stringResource(Res.string.cancel))
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (showTimePicker) {
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        descriptionViewModel.changeTimeToWatch(timePickerState.hour, timePickerState.minute)
                        showTimePicker = false
                    }) {
                        Text(stringResource(Res.string.accept))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showTimePicker = false }) {
                        Text(stringResource(Res.string.cancel))
                    }
                },
                text = {
                    TimePicker(state = timePickerState)
                }
            )
        }
    }
}

private fun epochMillisToDateString(millis: Long): String {
    var days = (millis / 86400000L).toInt()
    days += 719468
    val era = (if (days >= 0) days else days - 146096) / 146097
    val doe = days - era * 146097
    val yoe = (doe - doe / 1460 + doe / 36524 - doe / 146096) / 365
    val y = yoe + era * 400
    val doy = doe - (365 * yoe + yoe / 4 - yoe / 100)
    val mp = (5 * doy + 2) / 153
    val d = doy - (153 * mp + 2) / 5 + 1
    val m = if (mp < 10) mp + 3 else mp - 9
    val year = if (m <= 2) y + 1 else y
    return "${d.toString().padStart(2, '0')}/${m.toString().padStart(2, '0')}/$year"
}

@Composable
private fun Content(descriptionViewModel: DescriptionViewModel) {

    val movie = descriptionViewModel.uiState.movieInfo
    val movieRoom = descriptionViewModel.uiState.roomMovieInfoForRetrofit

    val posterUrl = movie?.poster?.url ?: movieRoom?.posterUrl
    val yearVal = movie?.year?.toString() ?: movieRoom?.year?.toString() ?: ""
    val kpRating = movie?.rating?.kp?.toString() ?: movieRoom?.kpRating?.toString() ?: ""
    val genres = if (movie != null)
        movie.genres.joinToString(", ") { it.name ?: "" }
    else
        movieRoom?.genres?.joinToString(", ") { it.name ?: "" } ?: ""
    val movieTime = if (movie != null) "${movie.movieLength} мин" else "${movieRoom?.movieLength} мин"
    val age = if (movie != null) movie.ageRating?.let { "${it}+" } else movieRoom?.ageRating
    val budget = if (movie != null) movie.budget?.value?.let { "${it} $" } else movieRoom?.budget?.let { "${it} $" }
    val feesWorld = if (movie != null) movie.fees?.world?.value?.let { "${it} $" } else movieRoom?.feesWorld?.let { "${it} $" }
    val description = movie?.description ?: movieRoom?.description ?: ""
    val isSeries = movie?.typeNumber == 2 || movie?.typeNumber == 5
            || movieRoom?.typeNumber == 2 || movieRoom?.typeNumber == 5

    Spacer(modifier = Modifier.height(8.dp))

    // ── Постер + инфо ──────────────────────────────────────────────
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = posterUrl,
                contentDescription = stringResource(Res.string.poster_not_loaded),
                placeholder = painterResource(Res.drawable.poster_placeholder),
                error = painterResource(Res.drawable.poster_placeholder),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoItem(stringResource(Res.string.year), yearVal)
                InfoItem(stringResource(Res.string.kp_rating_short), kpRating)
                if (genres.isNotBlank()) {
                    InfoItem(stringResource(Res.string.genre), genres, twoLines = true)
                }
                InfoItem(stringResource(Res.string.time), movieTime)
                if (!age.isNullOrBlank()) InfoItem(stringResource(Res.string.age), age!!)
                if (!budget.isNullOrBlank()) InfoItem(stringResource(Res.string.budget), budget!!, twoLines = true)
                if (!feesWorld.isNullOrBlank()) InfoItem(stringResource(Res.string.fees_world), feesWorld!!, twoLines = true)
            }
        }
    }

    // ── Описание ───────────────────────────────────────────────────
    if (description.isNotBlank()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Text(
                modifier = Modifier.padding(12.dp),
                text = description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }

    // ── Сезоны ─────────────────────────────────────────────────────
    if (isSeries) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = stringResource(Res.string.seasons_and_episodes),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(
                if (movie != null) movie.seasonsInfo ?: emptyList()
                else movieRoom?.seasonsInfo ?: emptyList()
            ) { _, item ->
                if (movie != null) seriesItemRow(item as SeasonsInfo, null)
                else seriesItemRow(null, item as SeasonsInfoForRetrofit)
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // ── Режиссёры ──────────────────────────────────────────────────
    Text(
        text = stringResource(Res.string.directors),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(
            if (movie != null) movie.persons!!.filter { it.enProfession == "director" }
            else movieRoom?.persons!!.filter { it.enProfession == "director" }
        ) { _, item ->
            if (movie != null) photoItemRow(item as Persons, null)
            else photoItemRow(null, item as PersonsForRetrofit)
        }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // ── Актёры ─────────────────────────────────────────────────────
    Text(
        text = stringResource(Res.string.actors),
        color = MaterialTheme.colorScheme.primary,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        itemsIndexed(
            if (movie != null) movie.persons!!.filter { it.enProfession == "actor" }
            else movieRoom?.persons!!.filter { it.enProfession == "actor" }
        ) { _, item ->
            if (movie != null) photoItemRow(item as Persons, null)
            else photoItemRow(null, item as PersonsForRetrofit)
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
private fun InfoItem(label: String, value: String, twoLines: Boolean = false) {
    if (twoLines) {
        Column(verticalArrangement = Arrangement.spacedBy(1.dp)) {
            Text(
                text = label,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    } else {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
                text = "$label:",
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun reminderDialogue(
    descriptionViewModel: DescriptionViewModel,
    authViewModel: AuthViewModel,
    journalsViewModel: JournalsViewModel,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit
) {
    val titleForNotification = stringResource(Res.string.time_to_watch)
    AlertDialog(
        icon = {},
        title = {
            Text(
                text = stringResource(Res.string.set_a_reminder),
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable { onDateClick() }
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "${stringResource(Res.string.select_a_date)}: ${descriptionViewModel.uiState.dateToWatch ?: ""}",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 16.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .clickable { onTimeClick() }
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "${stringResource(Res.string.select_a_time)}: ${descriptionViewModel.uiState.hoursToWatch ?: ""}:${descriptionViewModel.uiState.minutesToWatch ?: ""}",
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        fontSize = 16.sp
                    )
                }
            }
        },
        onDismissRequest = {
            descriptionViewModel.updateReminderDialogueStatus(false)
        },
        confirmButton = {
            TextButton(
                onClick = {
                    descriptionViewModel.addMovieToWatchToDB(
                        MoviesToWatchForRetrofit(
                            user = User(id = authViewModel.uiState.user!!.id),
                            movie = descriptionViewModel.uiState.roomMovieInfoForRetrofit,
                            reminderDate = descriptionViewModel.uiState.dateToWatch,
                            reminderHour = descriptionViewModel.uiState.hoursToWatch,
                            reminderMinute = descriptionViewModel.uiState.minutesToWatch
                        )
                    )
                    journalsViewModel.addMovieToWatchToLocalDB(
                        MoviesToWatch(
                            userId = authViewModel.uiState.user!!.id,
                            movieId = descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id!!,
                            reminderDate = descriptionViewModel.uiState.dateToWatch,
                            reminderHour = descriptionViewModel.uiState.hoursToWatch,
                            reminderMinute = descriptionViewModel.uiState.minutesToWatch
                        )
                    )
                    Napier.d("Уведомление сделано, дата: ${descriptionViewModel.uiState.dateToWatch} ; ${descriptionViewModel.uiState.hoursToWatch}:${descriptionViewModel.uiState.minutesToWatch}", tag = "ContentDescriptionScreen")
                    Napier.d("${descriptionViewModel.uiState.dateToWatch?.substringAfterLast("/")?.toInt() ?: 2024}", tag = "ContentDescriptionScreen")
                    Napier.d("${descriptionViewModel.uiState.dateToWatch?.substringAfter("/")?.substringBefore("/")?.removePrefix("0")?.toInt()?.minus(1) ?: 4}", tag = "ContentDescriptionScreen")
                    Napier.d("${descriptionViewModel.uiState.dateToWatch?.substringBefore("/")?.removePrefix("0")?.toInt() ?: 3}", tag = "ContentDescriptionScreen")
                    descriptionViewModel.updateReminderDialogueStatus(false)
                }
            ) {
                Text(stringResource(Res.string.remind))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    descriptionViewModel.updateReminderDialogueStatus(false)
                    descriptionViewModel.changeDateToWatch(null)
                    descriptionViewModel.changeTimeToWatch(null, null)
                }
            ) {
                Text(stringResource(Res.string.cancel))
            }
        }
    )
}
