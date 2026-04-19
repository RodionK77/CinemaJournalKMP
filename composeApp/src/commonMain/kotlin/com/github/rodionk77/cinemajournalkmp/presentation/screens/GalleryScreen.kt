package com.github.rodionk77.cinemajournalkmp.presentation.screens

import io.github.aakira.napier.Napier
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import cinemajournalkmp.composeapp.generated.resources.ic_add_circle
import cinemajournalkmp.composeapp.generated.resources.ic_delete
import cinemajournalkmp.composeapp.generated.resources.ic_expand_less
import cinemajournalkmp.composeapp.generated.resources.ic_expand_more
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.accept
import cinemajournalkmp.composeapp.generated.resources.add
import cinemajournalkmp.composeapp.generated.resources.age_rating
import cinemajournalkmp.composeapp.generated.resources.before
import cinemajournalkmp.composeapp.generated.resources.best_series
import cinemajournalkmp.composeapp.generated.resources.cancel
import cinemajournalkmp.composeapp.generated.resources.countries
import cinemajournalkmp.composeapp.generated.resources.delete_all
import cinemajournalkmp.composeapp.generated.resources.duration
import cinemajournalkmp.composeapp.generated.resources.enter_a_country
import cinemajournalkmp.composeapp.generated.resources.enter_a_genre
import cinemajournalkmp.composeapp.generated.resources.expand
import cinemajournalkmp.composeapp.generated.resources.filtered_search
import cinemajournalkmp.composeapp.generated.resources.from
import cinemajournalkmp.composeapp.generated.resources.kp_rating_short
import cinemajournalkmp.composeapp.generated.resources.type_of_movie
import cinemajournalkmp.composeapp.generated.resources.unknown_and_interesting
import cinemajournalkmp.composeapp.generated.resources.worth_a_look
import cinemajournalkmp.composeapp.generated.resources.year
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.github.rodionk77.cinemajournalkmp.presentation.items.cinemaItemColumn
import com.github.rodionk77.cinemajournalkmp.presentation.items.cinemaItemRow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryScreen(
    navController: NavController,
    galleryViewModel: GalleryViewModel,
    descriptionViewModel: DescriptionViewModel,
    authViewModel: AuthViewModel
) {

    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var showGenresDialog by rememberSaveable { mutableStateOf(false) }
    var dialogueGenresText by rememberSaveable { mutableStateOf("") }
    var showCountriesDialog by rememberSaveable { mutableStateOf(false) }
    var dialogueCountriesText by rememberSaveable { mutableStateOf("") }

    if (!galleryViewModel.uiState.drawerState) {
        scope.launch { drawerState.apply { close() } }
    } else {
        scope.launch { drawerState.apply { open() } }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(top = 24.dp, bottom = 24.dp)
                    .width(280.dp),
                drawerContainerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                    // ── Тип ──────────────────────────────────────────
                    var typeIsExpanded by rememberSaveable { mutableStateOf(false) }
                    val typeState1 = rememberSaveable { mutableStateOf(false) }
                    val typeState2 = rememberSaveable { mutableStateOf(false) }
                    val typeState3 = rememberSaveable { mutableStateOf(false) }
                    val typeState4 = rememberSaveable { mutableStateOf(false) }
                    val typeState5 = rememberSaveable { mutableStateOf(false) }

                    FilterSectionHeader(
                        title = stringResource(Res.string.type_of_movie),
                        expanded = typeIsExpanded,
                        onToggle = { typeIsExpanded = !typeIsExpanded }
                    )
                    if (typeIsExpanded) {
                        val chipColors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                        )
                        LazyRow(
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            item { FilterChip(typeState1.value, { typeState1.value = !typeState1.value }, { Text("movie", fontSize = 13.sp) }, colors = chipColors) }
                            item { FilterChip(typeState2.value, { typeState2.value = !typeState2.value }, { Text("tv-series", fontSize = 13.sp) }, colors = chipColors) }
                            item { FilterChip(typeState3.value, { typeState3.value = !typeState3.value }, { Text("cartoon", fontSize = 13.sp) }, colors = chipColors) }
                            item { FilterChip(typeState4.value, { typeState4.value = !typeState4.value }, { Text("animated-series", fontSize = 12.sp) }, colors = chipColors) }
                            item { FilterChip(typeState5.value, { typeState5.value = !typeState5.value }, { Text("anime", fontSize = 13.sp) }, colors = chipColors) }
                        }
                    }

                    HorizontalDivider()

                    // ── Год ──────────────────────────────────────────
                    var yearIsExpanded by rememberSaveable { mutableStateOf(false) }
                    var yearState1 by rememberSaveable { mutableStateOf("") }
                    var yearState2 by rememberSaveable { mutableStateOf("") }

                    FilterSectionHeader(
                        title = stringResource(Res.string.year),
                        expanded = yearIsExpanded,
                        onToggle = { yearIsExpanded = !yearIsExpanded }
                    )
                    if (yearIsExpanded) {
                        RangeInputRow(
                            value1 = yearState1, onValue1Change = { yearState1 = it },
                            value2 = yearState2, onValue2Change = { yearState2 = it },
                            label1 = stringResource(Res.string.from),
                            label2 = stringResource(Res.string.before)
                        )
                    }

                    HorizontalDivider()

                    // ── Рейтинг КП ───────────────────────────────────
                    var ratingIsExpanded by rememberSaveable { mutableStateOf(false) }
                    var ratingState1 by rememberSaveable { mutableStateOf("") }
                    var ratingState2 by rememberSaveable { mutableStateOf("") }

                    FilterSectionHeader(
                        title = stringResource(Res.string.kp_rating_short),
                        expanded = ratingIsExpanded,
                        onToggle = { ratingIsExpanded = !ratingIsExpanded }
                    )
                    if (ratingIsExpanded) {
                        RangeInputRow(
                            value1 = ratingState1, onValue1Change = { ratingState1 = it },
                            value2 = ratingState2, onValue2Change = { ratingState2 = it },
                            label1 = stringResource(Res.string.from),
                            label2 = stringResource(Res.string.before)
                        )
                    }

                    // ── Возраст ──────────────────────────────────────
                    var ageIsExpanded by rememberSaveable { mutableStateOf(false) }
                    var ageState1 by rememberSaveable { mutableStateOf("") }
                    var ageState2 by rememberSaveable { mutableStateOf("") }

                    if (authViewModel.uiState.user?.role ?: 0 == 0) {
                        HorizontalDivider()
                        FilterSectionHeader(
                            title = stringResource(Res.string.age_rating),
                            expanded = ageIsExpanded,
                            onToggle = { ageIsExpanded = !ageIsExpanded }
                        )
                        if (ageIsExpanded) {
                            RangeInputRow(
                                value1 = ageState1, onValue1Change = { ageState1 = it },
                                value2 = ageState2, onValue2Change = { ageState2 = it },
                                label1 = stringResource(Res.string.from),
                                label2 = stringResource(Res.string.before)
                            )
                        }
                    } else {
                        ageState1 = "0"
                        ageState2 = "12"
                    }

                    HorizontalDivider()

                    // ── Длительность ─────────────────────────────────
                    var timeIsExpanded by rememberSaveable { mutableStateOf(false) }
                    var timeState1 by rememberSaveable { mutableStateOf("") }
                    var timeState2 by rememberSaveable { mutableStateOf("") }

                    FilterSectionHeader(
                        title = stringResource(Res.string.duration),
                        expanded = timeIsExpanded,
                        onToggle = { timeIsExpanded = !timeIsExpanded }
                    )
                    if (timeIsExpanded) {
                        RangeInputRow(
                            value1 = timeState1, onValue1Change = { timeState1 = it },
                            value2 = timeState2, onValue2Change = { timeState2 = it },
                            label1 = stringResource(Res.string.from),
                            label2 = stringResource(Res.string.before)
                        )
                    }

                    HorizontalDivider()

                    // ── Жанры ─────────────────────────────────────────
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = if (galleryViewModel.uiState.genresList.isNotEmpty()) 0.dp else 12.dp)
                    ) {
                        Text(
                            text = "Жанры",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .clickable { showGenresDialog = true }
                                .padding(4.dp),
                            painter = painterResource(Res.drawable.ic_add_circle),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(Res.string.add)
                        )
                        if (galleryViewModel.uiState.genresList.isNotEmpty()) {
                            Icon(
                                modifier = Modifier
                                    .clickable { galleryViewModel.removeAllGenres() },
                                painter = painterResource(Res.drawable.ic_delete),
                                contentDescription = stringResource(Res.string.delete_all)
                            )
                        }
                    }
                    if (galleryViewModel.uiState.genresList.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            itemsIndexed(galleryViewModel.uiState.genresList) { _, item ->
                                FilterChip(
                                    selected = true,
                                    onClick = { galleryViewModel.removeGenre(item) },
                                    label = { Text(item, fontSize = 13.sp) }
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    // ── Страны ────────────────────────────────────────
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = if (galleryViewModel.uiState.countriesList.isNotEmpty()) 0.dp else 12.dp)
                    ) {
                        Text(
                            text = stringResource(Res.string.countries),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            modifier = Modifier
                                .clip(RoundedCornerShape(50))
                                .clickable { showCountriesDialog = true }
                                .padding(4.dp),
                            painter = painterResource(Res.drawable.ic_add_circle),
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = stringResource(Res.string.add)
                        )
                        if (galleryViewModel.uiState.countriesList.isNotEmpty()) {
                            Icon(
                                modifier = Modifier
                                    .clickable { galleryViewModel.removeAllCountries() },
                                painter = painterResource(Res.drawable.ic_delete),
                                contentDescription = stringResource(Res.string.delete_all)
                            )
                        }
                    }
                    if (galleryViewModel.uiState.countriesList.isNotEmpty()) {
                        LazyRow(
                            modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            itemsIndexed(galleryViewModel.uiState.countriesList) { _, item ->
                                FilterChip(
                                    selected = true,
                                    onClick = { galleryViewModel.removeCountry(item) },
                                    label = { Text(item, fontSize = 13.sp) }
                                )
                            }
                        }
                    }

                    HorizontalDivider()

                    // ── Кнопка поиска ─────────────────────────────────
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        shape = RoundedCornerShape(12.dp),
                        onClick = {
                            var typeList: MutableList<String> = mutableListOf()
                            galleryViewModel.changeSearchState(true)
                            if (typeState1.value) typeList.add("movie")
                            if (typeState2.value) typeList.add("tv-series")
                            if (typeState3.value) typeList.add("cartoon")
                            if (typeState4.value) typeList.add("animated-series")
                            if (typeState5.value) typeList.add("anime")

                            val year = if (yearState1 != "" && yearState2 != "") "$yearState1-$yearState2" else null
                            val rating = if (ratingState1 != "" && ratingState2 != "") "$ratingState1-$ratingState2" else null
                            val ageRating = if (ageState1 != "" && ageState2 != "") "$ageState1-$ageState2" else null
                            val time = if (timeState1 != "" && timeState2 != "") "$timeState1-$timeState2" else null
                            val genres = galleryViewModel.uiState.genresList.ifEmpty { null }
                            val countries = galleryViewModel.uiState.countriesList.ifEmpty { null }

                            Napier.d("type: $typeList; year: $year; rating: $rating; age: $ageRating; time: $time; genres: $genres; countries $countries", tag = "GalleryScreen")

                            galleryViewModel.changeQueryGo(true)
                            galleryViewModel.refreshFilteredMovies(typeList, year, rating, ageRating, time, genres, countries)
                            typeList = mutableListOf()
                        }
                    ) {
                        Text(stringResource(Res.string.filtered_search), fontSize = 16.sp)
                    }
                }
            }
        },
    ) {
        if (!galleryViewModel.uiState.isQueryGo) {
            BasicScreen(navController, galleryViewModel, descriptionViewModel, authViewModel)
        } else {
            SearchScreen(navController, galleryViewModel, descriptionViewModel)
        }
    }

    if (showGenresDialog) {
        AlertDialog(
            icon = {},
            title = { Text(stringResource(Res.string.enter_a_genre)) },
            text = {
                TextField(
                    value = dialogueGenresText,
                    onValueChange = { dialogueGenresText = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    ),
                    singleLine = true,
                )
            },
            onDismissRequest = { showGenresDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (dialogueGenresText != "") {
                        galleryViewModel.addGenre(dialogueGenresText)
                        dialogueGenresText = ""
                        showGenresDialog = false
                    }
                }) { Text(stringResource(Res.string.accept)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogueGenresText = ""
                    showGenresDialog = false
                }) { Text(stringResource(Res.string.cancel)) }
            }
        )
    }

    if (showCountriesDialog) {
        AlertDialog(
            icon = {},
            title = { Text(stringResource(Res.string.enter_a_country)) },
            text = {
                TextField(
                    value = dialogueCountriesText,
                    onValueChange = { dialogueCountriesText = it },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    ),
                    singleLine = true,
                )
            },
            onDismissRequest = { showCountriesDialog = false },
            confirmButton = {
                TextButton(onClick = {
                    if (dialogueCountriesText != "") {
                        galleryViewModel.addCountry(dialogueCountriesText)
                        dialogueCountriesText = ""
                        showCountriesDialog = false
                    }
                }) { Text(stringResource(Res.string.accept)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    dialogueCountriesText = ""
                    showCountriesDialog = false
                }) { Text(stringResource(Res.string.cancel)) }
            }
        )
    }
}

@Composable
private fun FilterSectionHeader(title: String, expanded: Boolean, onToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            painter = painterResource(if (expanded) Res.drawable.ic_expand_less else Res.drawable.ic_expand_more),
            contentDescription = stringResource(Res.string.expand)
        )
    }
}

@Composable
private fun RangeInputRow(
    value1: String, onValue1Change: (String) -> Unit,
    value2: String, onValue2Change: (String) -> Unit,
    label1: String, label2: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp, bottom = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = value1,
            onValueChange = onValue1Change,
            label = { Text(label1, fontSize = 11.sp) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(fontSize = 14.sp),
        )
        Text("—", fontSize = 16.sp)
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = value2,
            onValueChange = onValue2Change,
            label = { Text(label2, fontSize = 11.sp) },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(8.dp),
            textStyle = TextStyle(fontSize = 14.sp),
        )
    }
}

@Composable
fun BasicScreen(
    navController: NavController,
    galleryViewModel: GalleryViewModel,
    descriptionViewModel: DescriptionViewModel,
    authViewModel: AuthViewModel
) {

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(Res.string.worth_a_look),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(galleryViewModel.uiState.topMoviesInfo?.toList()!!) { _, item ->
                    cinemaItemRow(item = item, galleryViewModel = galleryViewModel, descriptionViewModel, navController = navController)
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(Res.string.unknown_and_interesting),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(galleryViewModel.uiState.topInterestedMoviesInfo?.toList()!!) { _, item ->
                    cinemaItemRow(item = item, galleryViewModel = galleryViewModel, descriptionViewModel, navController = navController)
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = stringResource(Res.string.best_series),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                itemsIndexed(galleryViewModel.uiState.topSeriesInfo?.toList()!!) { _, item ->
                    cinemaItemRow(item = item, galleryViewModel = galleryViewModel, descriptionViewModel, navController = navController)
                }
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        if (authViewModel.uiState.user?.role ?: 0 == 0) {
            nonKidsTops(navController = navController, galleryViewModel = galleryViewModel, descriptionViewModel = descriptionViewModel)
        } else {
            kidsTops(navController = navController, galleryViewModel = galleryViewModel, descriptionViewModel = descriptionViewModel)
        }

        if (galleryViewModel.uiState.isSearch) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.6f))
                    .pointerInput(Unit) {}
            )
        }
    }
}

@Composable
fun nonKidsTops(navController: NavController, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel) {}

@Composable
fun kidsTops(navController: NavController, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel) {}

@Composable
fun SearchScreen(
    navController: NavController,
    galleryViewModel: GalleryViewModel,
    descriptionViewModel: DescriptionViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            itemsIndexed(galleryViewModel.uiState.searchMoviesInfo?.toList()!!) { _, item ->
                cinemaItemColumn(
                    item = item,
                    navController = navController,
                    galleryViewModel = galleryViewModel,
                    descriptionViewModel = descriptionViewModel
                )
            }
        }
    }
}
