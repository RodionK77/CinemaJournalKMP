package com.github.rodionk77.cinemajournalkmp.presentation.screens

import io.github.aakira.napier.Napier
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import cinemajournalkmp.composeapp.generated.resources.ic_add
import cinemajournalkmp.composeapp.generated.resources.ic_delete
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.accept
import cinemajournalkmp.composeapp.generated.resources.add
import cinemajournalkmp.composeapp.generated.resources.add_to_wanted_to_watch
import cinemajournalkmp.composeapp.generated.resources.add_to_watched
import cinemajournalkmp.composeapp.generated.resources.age
import cinemajournalkmp.composeapp.generated.resources.budget
import cinemajournalkmp.composeapp.generated.resources.cancel
import cinemajournalkmp.composeapp.generated.resources.countries
import cinemajournalkmp.composeapp.generated.resources.delete_all
import cinemajournalkmp.composeapp.generated.resources.description
import cinemajournalkmp.composeapp.generated.resources.fees_world
import cinemajournalkmp.composeapp.generated.resources.genre
import cinemajournalkmp.composeapp.generated.resources.monosyllabic_text
import cinemajournalkmp.composeapp.generated.resources.name
import cinemajournalkmp.composeapp.generated.resources.rating
import cinemajournalkmp.composeapp.generated.resources.time
import cinemajournalkmp.composeapp.generated.resources.year
import com.example.cinemajournal.ui.theme.screens.viewmodels.AdditionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.github.rodionk77.cinemajournalkmp.data.models.Countries
import com.github.rodionk77.cinemajournalkmp.data.models.Genres
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.CountriesForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.GenresForRetrofit
import org.jetbrains.compose.resources.stringResource

@Composable
fun ContentAdditionScreen(navController: NavController, additionViewModel: AdditionViewModel, authViewModel: AuthViewModel) {

    var showDialog by rememberSaveable { mutableStateOf(false) }
    var dialogueText by rememberSaveable { mutableStateOf("") }
    var dialogueSate by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(Modifier.height(4.dp))

        // ── Название ──────────────────────────────────────────────────
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = additionViewModel.uiState.nameText,
            onValueChange = { additionViewModel.changeNameText(it) },
            label = { Text(stringResource(Res.string.name)) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        // ── Год + Рейтинг ─────────────────────────────────────────────
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = additionViewModel.uiState.yearText,
                onValueChange = { additionViewModel.changeYearText(it) },
                label = { Text(stringResource(Res.string.year)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = additionViewModel.uiState.ratingText.toString(),
                onValueChange = { additionViewModel.changeRatingText(it.toDouble()) },
                label = { Text(stringResource(Res.string.rating)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // ── Длительность + Возраст ────────────────────────────────────
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = additionViewModel.uiState.timeText.toString(),
                onValueChange = { additionViewModel.changeTimeText(it.toInt()) },
                label = { Text(stringResource(Res.string.time)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = additionViewModel.uiState.ageRatingText,
                onValueChange = { additionViewModel.changeAgeRatingText(it) },
                label = { Text(stringResource(Res.string.age)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // ── Бюджет + Сборы ────────────────────────────────────────────
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = additionViewModel.uiState.budgetText.toString(),
                onValueChange = { additionViewModel.changeBudgetText(it.toInt()) },
                label = { Text(stringResource(Res.string.budget)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = additionViewModel.uiState.feesWorldText.toString(),
                onValueChange = { additionViewModel.changeFeesWorldText(it.toLong()) },
                label = { Text(stringResource(Res.string.fees_world)) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = RoundedCornerShape(12.dp)
            )
        }

        // ── Жанры ─────────────────────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(Res.string.genre),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .clickable { dialogueSate = true; showDialog = true }
                    .padding(4.dp),
                painter = painterResource(Res.drawable.ic_add),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(Res.string.add)
            )
            if (additionViewModel.uiState.genres.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .scale(0.85f)
                        .clickable { additionViewModel.changeGenres(mutableListOf()) },
                    painter = painterResource(Res.drawable.ic_delete),
                    contentDescription = stringResource(Res.string.delete_all)
                )
            }
        }
        if (additionViewModel.uiState.genres.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(additionViewModel.uiState.genres) { _, item ->
                    FilterChip(
                        modifier = Modifier.scale(0.9f),
                        selected = true,
                        onClick = {},
                        label = { Text(item.name ?: "", fontSize = 13.sp) }
                    )
                }
            }
        }

        // ── Страны ────────────────────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(Res.string.countries),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .clickable { dialogueSate = false; showDialog = true }
                    .padding(4.dp),
                painter = painterResource(Res.drawable.ic_add),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(Res.string.add)
            )
            if (additionViewModel.uiState.countries.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .scale(0.85f)
                        .clickable { additionViewModel.changeCountries(mutableListOf()) },
                    painter = painterResource(Res.drawable.ic_delete),
                    contentDescription = stringResource(Res.string.delete_all)
                )
            }
        }
        if (additionViewModel.uiState.countries.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(additionViewModel.uiState.countries) { _, item ->
                    FilterChip(
                        modifier = Modifier.scale(0.9f),
                        selected = true,
                        onClick = {},
                        label = { Text(item.name ?: "", fontSize = 13.sp) }
                    )
                }
            }
        }

        // ── Описание ──────────────────────────────────────────────────
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = additionViewModel.uiState.descriptionText,
            onValueChange = { additionViewModel.changeDescriptionText(it) },
            label = { Text(stringResource(Res.string.description)) },
            shape = RoundedCornerShape(12.dp),
            minLines = 3
        )

        // ── Кнопки ────────────────────────────────────────────────────
        Button(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = { additionViewModel.addToWatched(authViewModel.uiState.user?.id ?: 0) }
        ) {
            Text(stringResource(Res.string.add_to_watched), fontSize = 15.sp)
        }

        Button(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = { additionViewModel.addToWatch(authViewModel.uiState.user?.id ?: 0) }
        ) {
            Text(stringResource(Res.string.add_to_wanted_to_watch), fontSize = 15.sp)
        }

        Spacer(Modifier.height(8.dp))

        // ── Диалог добавления жанра / страны ──────────────────────────
        if (showDialog) {
            Dialog(onDismissRequest = { showDialog = false; dialogueText = "" }) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.monosyllabic_text),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        OutlinedTextField(
                            value = dialogueText,
                            onValueChange = { dialogueText = it },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                        ) {
                            TextButton(onClick = { dialogueText = ""; showDialog = false }) {
                                Text(stringResource(Res.string.cancel))
                            }
                            Button(
                                onClick = {
                                    if (dialogueSate) {
                                        additionViewModel.addGenre(GenresForRetrofit(genresId = null, contentId = null, name = dialogueText))
                                    } else {
                                        additionViewModel.addCountry(CountriesForRetrofit(countriesId = null, contentId = null, name = dialogueText))
                                    }
                                    dialogueText = ""
                                    showDialog = false
                                },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(stringResource(Res.string.accept))
                            }
                        }
                    }
                }
            }
        }
    }
}

fun CountriesForRetrofit.toCountries(): Countries {
    return Countries(name)
}

fun MutableList<CountriesForRetrofit>.toCountriesList(): List<Countries> {
    return this.map { it.toCountries() }
}

fun GenresForRetrofit.toGenres(): Genres {
    return Genres(name)
}

fun MutableList<GenresForRetrofit>.toGenresList(): List<Genres> {
    return this.map { it.toGenres() }
}
