@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.github.rodionk77.cinemajournalkmp.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import cinemajournalkmp.composeapp.generated.resources.ic_add
import cinemajournalkmp.composeapp.generated.resources.ic_delete
import cinemajournalkmp.composeapp.generated.resources.ic_expand_less
import cinemajournalkmp.composeapp.generated.resources.ic_expand_more
import cinemajournalkmp.composeapp.generated.resources.ic_grade_filled
import cinemajournalkmp.composeapp.generated.resources.ic_grade_outlined
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.ui.window.Dialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.accept
import cinemajournalkmp.composeapp.generated.resources.add
import cinemajournalkmp.composeapp.generated.resources.age
import cinemajournalkmp.composeapp.generated.resources.budget
import cinemajournalkmp.composeapp.generated.resources.cancel
import cinemajournalkmp.composeapp.generated.resources.date_of_watch
import cinemajournalkmp.composeapp.generated.resources.delete_all
import cinemajournalkmp.composeapp.generated.resources.description
import cinemajournalkmp.composeapp.generated.resources.dislikes
import cinemajournalkmp.composeapp.generated.resources.expand
import cinemajournalkmp.composeapp.generated.resources.fees_world
import cinemajournalkmp.composeapp.generated.resources.genre
import cinemajournalkmp.composeapp.generated.resources.kp_rating_short
import cinemajournalkmp.composeapp.generated.resources.likes
import cinemajournalkmp.composeapp.generated.resources.monosyllabic_text
import cinemajournalkmp.composeapp.generated.resources.notes
import cinemajournalkmp.composeapp.generated.resources.poster_not_loaded
import cinemajournalkmp.composeapp.generated.resources.poster_placeholder
import cinemajournalkmp.composeapp.generated.resources.save
import cinemajournalkmp.composeapp.generated.resources.time
import com.github.anandkumarkparmar.ratingbar.RatingBar
import com.github.anandkumarkparmar.ratingbar.RatingBarDefaults
import com.github.anandkumarkparmar.ratingbar.animations
import com.github.anandkumarkparmar.ratingbar.behavior
import com.github.anandkumarkparmar.ratingbar.core.RatingBarConfig
import com.github.anandkumarkparmar.ratingbar.style
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Dislikes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Likes
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.Review
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.ReviewForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.presentation.items.wordItemRow
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentReviewScreen(navController: NavController, reviewViewModel: ReviewViewModel, authViewModel: AuthViewModel) {
    Content(reviewViewModel, authViewModel)
}

@Composable
private fun Content(reviewViewModel: ReviewViewModel, authViewModel: AuthViewModel) {
    val focusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSecondary)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
    ) {

        val descriptionIsExpanded = rememberSaveable { mutableStateOf(false) }

        if (reviewViewModel.uiState.likesForReview == null) {
            reviewViewModel.changeLikes(reviewViewModel.uiState.roomMovieInfoForRetrofit?.reviews?.likes?.toMutableList())
        }
        if (reviewViewModel.uiState.dislikesForReview == null) {
            reviewViewModel.changeDislikes(reviewViewModel.uiState.roomMovieInfoForRetrofit?.reviews?.dislikes?.toMutableList())
        }
        if (reviewViewModel.uiState.rating == null) {
            reviewViewModel.changeRating(reviewViewModel.uiState.roomMovieInfoForRetrofit?.reviews?.rating?.toFloat() ?: 0.0f)
        }
        if (reviewViewModel.uiState.reviewText == null) {
            reviewViewModel.changeReviewText(reviewViewModel.uiState.roomMovieInfoForRetrofit?.reviews?.notes ?: "")
        }
        if (reviewViewModel.uiState.dateWatched == null) {
            reviewViewModel.changeDate(reviewViewModel.uiState.roomMovieInfoForRetrofit?.reviews?.dateWatched)
        }

        var showDialog by rememberSaveable { mutableStateOf(false) }
        var dialogueText by rememberSaveable { mutableStateOf("") }
        var dialogueSate by rememberSaveable { mutableStateOf(false) }
        var showDatePicker by rememberSaveable { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState()

        val movie = reviewViewModel.uiState.roomMovieInfoForRetrofit
        val genres = movie?.genres?.joinToString(", ") { it.name ?: "" } ?: ""

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
                    model = movie?.posterUrl,
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
                    InfoItem("Год", movie?.year?.toString() ?: "")
                    InfoItem(stringResource(Res.string.kp_rating_short), movie?.kpRating?.toString() ?: "")
                    if (genres.isNotBlank()) {
                        InfoItem(stringResource(Res.string.genre), genres, twoLines = true)
                    }
                    InfoItem(stringResource(Res.string.time), movie?.movieLength?.toString() ?: "")
                    InfoItem(stringResource(Res.string.age), "${movie?.ageRating}+")
                    InfoItem(stringResource(Res.string.budget), "${movie?.budget}$", twoLines = true)
                    InfoItem(stringResource(Res.string.fees_world), "${movie?.feesWorld}$", twoLines = true)

                    Row(
                        modifier = Modifier.clickable {
                            descriptionIsExpanded.value = !descriptionIsExpanded.value
                        },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(Res.string.description),
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.secondary,
                        )
                        Icon(
                            modifier = Modifier.scale(0.8f),
                            painter = painterResource(
                                if (descriptionIsExpanded.value) Res.drawable.ic_expand_less
                                else Res.drawable.ic_expand_more
                            ),
                            contentDescription = stringResource(Res.string.expand)
                        )
                    }
                }
            }
        }

        // ── Описание ─────────────────────────────────────
        if (descriptionIsExpanded.value) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            ) {
                Text(
                    modifier = Modifier.padding(12.dp),
                    text = movie?.description ?: "",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 15.sp,
                    lineHeight = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Рейтинг ────────────────────────────────────────────────────
        RatingBar(
            value = reviewViewModel.uiState.rating ?: 0.0f,
            onValueChange = { reviewViewModel.changeRating(it) },
            config = RatingBarConfig(max = 10, step = 0.5f),
            style = RatingBarDefaults.style(itemSize = RatingBarDefaults.SizeMedium),
            animations = RatingBarDefaults.animations(enabled = true),
            behavior = RatingBarDefaults.behavior(hapticFeedback = true),
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ── Дата просмотра ─────────────────────────────────────────────
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable { showDatePicker = true }
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${stringResource(Res.string.date_of_watch)}: ${reviewViewModel.uiState.dateWatched ?: ""}",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Плюсы ─────────────────────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(Res.string.likes),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        dialogueSate = true
                        showDialog = !showDialog
                    }
                    .padding(8.dp),
                painter = painterResource(Res.drawable.ic_add),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(Res.string.add)
            )
            if (reviewViewModel.uiState.likesForReview?.isNotEmpty() == true) {
                Icon(
                    modifier = Modifier
                        .scale(0.8f)
                        .clickable { reviewViewModel.changeLikes(mutableListOf()) },
                    painter = painterResource(Res.drawable.ic_delete),
                    contentDescription = stringResource(Res.string.delete_all)
                )
            }
        }
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(reviewViewModel.uiState.likesForReview ?: emptyList()) { _, item ->
                wordItemRow(text = item.description ?: "")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Минусы ────────────────────────────────────────────────────
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(Res.string.dislikes),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                modifier = Modifier
                    .clickable {
                        dialogueSate = false
                        showDialog = !showDialog
                    }
                    .padding(8.dp),
                painter = painterResource(Res.drawable.ic_add),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = stringResource(Res.string.add)
            )
            if (reviewViewModel.uiState.dislikesForReview?.isNotEmpty() == true) {
                Icon(
                    modifier = Modifier
                        .scale(0.8f)
                        .clickable { reviewViewModel.changeDislikes(mutableListOf()) },
                    painter = painterResource(Res.drawable.ic_delete),
                    contentDescription = stringResource(Res.string.delete_all)
                )
            }
        }
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(reviewViewModel.uiState.dislikesForReview ?: emptyList()) { _, item ->
                wordItemRow(text = item.description ?: "")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ── Заметки ───────────────────────────────────────────────────
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(Res.string.notes),
            color = MaterialTheme.colorScheme.primary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = reviewViewModel.uiState.reviewText ?: "",
            onValueChange = { reviewViewModel.changeReviewText(it) },
            shape = RoundedCornerShape(12.dp),
            minLines = 3,
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ── Сохранить ─────────────────────────────────────────────────
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = {
                reviewViewModel.addReviewToDB(
                    ReviewForRetrofit(
                        user = authViewModel.uiState.user,
                        movie = RoomMovieInfoForRetrofit(
                            id = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id ?: 0
                        ),
                        contentId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id ?: 0,
                        rating = reviewViewModel.uiState.rating?.toDouble() ?: 0.0,
                        notes = reviewViewModel.uiState.reviewText,
                        likes = reviewViewModel.uiState.likesForReview,
                        dislikes = reviewViewModel.uiState.dislikesForReview,
                        dateWatched = reviewViewModel.uiState.dateWatched
                    )
                )
                reviewViewModel.saveReviewToLocalDB(
                    review = Review(
                        userId = authViewModel.uiState.user?.id ?: 0,
                        movieId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id ?: 0,
                        contentId = reviewViewModel.uiState.roomMovieInfoForRetrofit!!.id ?: 0,
                        rating = reviewViewModel.uiState.rating?.toDouble() ?: 0.0,
                        notes = reviewViewModel.uiState.reviewText,
                        dateWatched = reviewViewModel.uiState.dateWatched
                    )
                )
                reviewViewModel.deleteLikesFromLocalDB(reviewViewModel.uiState.roomMovieInfoForRetrofit?.id ?: 0)
                reviewViewModel.uiState.likesForReview?.forEach {
                    reviewViewModel.saveLikesToLocalDB(it)
                }
                reviewViewModel.deleteDislikesFromLocalDB(reviewViewModel.uiState.roomMovieInfoForRetrofit?.id ?: 0)
                reviewViewModel.uiState.dislikesForReview?.forEach {
                    reviewViewModel.saveDislikesToLocalDB(it)
                }
            }
        ) {
            Text(stringResource(Res.string.save), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── Диалог добавления плюса/минуса ────────────────────────────
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
                                        reviewViewModel.addLike(
                                            Likes(
                                                userId = authViewModel.uiState.user?.id ?: 0,
                                                movieId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id ?: 0,
                                                description = dialogueText
                                            )
                                        )
                                    } else {
                                        reviewViewModel.addDislike(
                                            Dislikes(
                                                userId = authViewModel.uiState.user?.id ?: 0,
                                                movieId = reviewViewModel.uiState.roomMovieInfoForRetrofit?.id ?: 0,
                                                description = dialogueText
                                            )
                                        )
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

        // ── Выбор даты ────────────────────────────────────────────────
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            reviewViewModel.changeDate(epochMillisToDateString(millis))
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
    }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomDialog() {}

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
