package com.github.rodionk77.cinemajournalkmp.presentation

import io.github.aakira.napier.Napier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import org.jetbrains.compose.resources.painterResource
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.add_to_wanted_to_watch
import cinemajournalkmp.composeapp.generated.resources.add_to_watched
import cinemajournalkmp.composeapp.generated.resources.back
import cinemajournalkmp.composeapp.generated.resources.delete_from_wanted_to_watch
import cinemajournalkmp.composeapp.generated.resources.delete_from_watched
import cinemajournalkmp.composeapp.generated.resources.dislikes
import cinemajournalkmp.composeapp.generated.resources.expand
import cinemajournalkmp.composeapp.generated.resources.gallery
import cinemajournalkmp.composeapp.generated.resources.ic_journal
import cinemajournalkmp.composeapp.generated.resources.journals
import cinemajournalkmp.composeapp.generated.resources.likes
import cinemajournalkmp.composeapp.generated.resources.notes
import cinemajournalkmp.composeapp.generated.resources.rating
import cinemajournalkmp.composeapp.generated.resources.remind_to_watch
import cinemajournalkmp.composeapp.generated.resources.review
import cinemajournalkmp.composeapp.generated.resources.search
import cinemajournalkmp.composeapp.generated.resources.search_query
import cinemajournalkmp.composeapp.generated.resources.ic_add_box
import cinemajournalkmp.composeapp.generated.resources.ic_arrow_back
import cinemajournalkmp.composeapp.generated.resources.ic_close
import cinemajournalkmp.composeapp.generated.resources.ic_contacts
import cinemajournalkmp.composeapp.generated.resources.ic_more_vert
import cinemajournalkmp.composeapp.generated.resources.ic_print
import cinemajournalkmp.composeapp.generated.resources.ic_search
import cinemajournalkmp.composeapp.generated.resources.ic_tune
import com.example.cinemajournal.ui.theme.screens.viewmodels.AdditionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatch
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.MoviesToWatchForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfo
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.RoomMovieInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.User
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMovies
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.WatchedMoviesForRetrofit
import com.github.rodionk77.cinemajournalkmp.createReviewHtml
import com.github.rodionk77.cinemajournalkmp.rememberPrintAction
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalsToolbar(scrollBehavior: TopAppBarScrollBehavior, navController: NavController, journalsViewModel: JournalsViewModel, authViewModel: AuthViewModel){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                stringResource(Res.string.journals),
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing= 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        actions = {
            if(authViewModel.uiState.user?.role == 0){
                IconButton(onClick = {
                    navController.navigate("ContentAdditionScreen")
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_add_box),
                        contentDescription = "Localized description"
                    )
                }
            }
            IconButton(onClick = {
                journalsViewModel.changeAccountDialogState(true)
            }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_contacts),
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationsToolbar(scrollBehavior: TopAppBarScrollBehavior) {
    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "Рекомендации",
                fontSize = 32.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdditionToolbar(scrollBehavior: TopAppBarScrollBehavior, navController: NavController, additionViewModel: AdditionViewModel){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Text("Добавление фильма", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 24.sp)
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
                additionViewModel.changeId(null)
                //descriptionViewModel.refreshCurrentMovieInfo(null)
                //descriptionViewModel.refreshCurrentMovieInfoRoom(null)
            }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            /*IconButton(onClick = { /* do something */ }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = "Localized description"
                )
            }*/
            IconButton(onClick = {
                navController.navigateUp()
                additionViewModel.clearAllField()
            }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_close),
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryToolbar(scrollBehavior: TopAppBarScrollBehavior, galleryViewModel: GalleryViewModel, authViewModel: AuthViewModel){

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            if(!galleryViewModel.uiState.isSearch){
                Text(
                    text = stringResource(Res.string.gallery),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing= 8.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                val focusManager = LocalFocusManager.current
                val doSearch = {
                    val q = galleryViewModel.uiState.searchQuery
                    Napier.d("doSearch нажат, query='$q'", tag = "GalleryToolbar")
                    if (q.isNotEmpty()) {
                        focusManager.clearFocus()
                        galleryViewModel.changeQueryGo(true)
                        galleryViewModel.refreshSearchMovies(q)
                    }
                }
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = galleryViewModel.uiState.searchQuery,
                    placeholder = {Text(
                        text = stringResource(Res.string.search_query),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 24.sp
                    )},
                    onValueChange = {
                        galleryViewModel.changeSearchQuery(it)
                    },
                    singleLine = true,
                    textStyle = TextStyle.Default.copy(fontSize = 26.sp, fontWeight = FontWeight.SemiBold),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = { doSearch() }),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.primary,
                        unfocusedTextColor = MaterialTheme.colorScheme.primary,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    trailingIcon = {
                        if(galleryViewModel.uiState.searchQuery.isNotEmpty()){
                            ElevatedButton(onClick = { doSearch() },
                            modifier = Modifier.scale(0.8f)) {
                                Text(stringResource(Res.string.search), fontSize = 20.sp)
                            }
                        }
                    }
                )
            }
        },
        navigationIcon = {
            if(galleryViewModel.uiState.isSearch){
                IconButton(onClick = {
                    if(galleryViewModel.uiState.drawerState){
                        galleryViewModel.changeDrawerState(!galleryViewModel.uiState.drawerState)
                    }

                    galleryViewModel.changeSearchState(false)
                    galleryViewModel.changeSearchQuery("")
                    galleryViewModel.changeQueryGo(false)
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_back),
                        contentDescription = stringResource(Res.string.back)
                    )
                }
            }
        },
        actions = {
            if(galleryViewModel.uiState.isSearch || authViewModel.uiState.user?.role?:0 == 1){
                IconButton(onClick = {
                    galleryViewModel.changeDrawerState(!galleryViewModel.uiState.drawerState)
                    Napier.d(galleryViewModel.uiState.drawerState.toString(), tag = "Toolbars")
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_tune),
                        contentDescription = "Localized description"
                    )
                }
            }

            if(!galleryViewModel.uiState.isSearch && authViewModel.uiState.user?.role?:0 == 0){
                IconButton(onClick = {
                    galleryViewModel.changeSearchState(true)
                }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_search),
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        scrollBehavior = if (!galleryViewModel.uiState.isSearch) scrollBehavior else TopAppBarDefaults.pinnedScrollBehavior()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentToolbar(navController: NavController, currentDestination: String, descriptionViewModel: DescriptionViewModel, reviewViewModel: ReviewViewModel, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel){

    if(descriptionViewModel.uiState.movieInfo != null){
        descriptionViewModel.checkMovieToWatch(descriptionViewModel.uiState.movieInfo?.id?: 0, authViewModel.uiState.user?.id?:0)
        descriptionViewModel.checkWatchedMovie(descriptionViewModel.uiState.movieInfo?.id?: 0, authViewModel.uiState.user?.id?:0)
    } else {
        descriptionViewModel.checkMovieToWatch(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?: 0, authViewModel.uiState.user?.id?:0)
        descriptionViewModel.checkWatchedMovie(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.id?: 0, authViewModel.uiState.user?.id?:0)
    }

    var toWatchText = if(!descriptionViewModel.uiState.movieToWatchStatus) stringResource(Res.string.add_to_wanted_to_watch) else stringResource(Res.string.delete_from_wanted_to_watch)
    var watchedText = if(!descriptionViewModel.uiState.watchedMovieStatus) stringResource(Res.string.add_to_watched) else stringResource(Res.string.delete_from_watched)


    val review_string_for_print = stringResource(Res.string.review)
    val rating_string_for_print = stringResource(Res.string.rating)
    val likes_string_for_print = stringResource(Res.string.likes)
    val dislikes_string_for_print = stringResource(Res.string.dislikes)
    val notes_string_for_print = stringResource(Res.string.notes)

    val reviewHtml = createReviewHtml(reviewViewModel, review_string_for_print, rating_string_for_print, likes_string_for_print, dislikes_string_for_print, notes_string_for_print)
    val doPrint = rememberPrintAction(reviewHtml, reviewViewModel.uiState.roomMovieInfoForRetrofit?.name ?: "")

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
        ),
        title = {
            Column {
                if(descriptionViewModel.uiState.movieInfo != null){
                    Text(descriptionViewModel.uiState.movieInfo?.name ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 24.sp)
                    if(descriptionViewModel.uiState.movieInfo?.enName == "" || descriptionViewModel.uiState.movieInfo?.enName == null){
                        Text(descriptionViewModel.uiState.movieInfo?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                    }else Text(descriptionViewModel.uiState.movieInfo?.enName ?: descriptionViewModel.uiState.movieInfo?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                } else {
                    Text(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.name ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 24.sp)
                    if(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.enName == "" || descriptionViewModel.uiState.roomMovieInfoForRetrofit?.enName == null){
                        Text(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                    }else Text(descriptionViewModel.uiState.roomMovieInfoForRetrofit?.enName ?: descriptionViewModel.uiState.roomMovieInfoForRetrofit?.alternativeName ?: " ", maxLines = 1, overflow = TextOverflow.Ellipsis, fontSize = 16.sp, color = MaterialTheme.colorScheme.secondary)
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigateUp()
                //descriptionViewModel.refreshCurrentMovieInfo(null)
                //descriptionViewModel.refreshCurrentMovieInfoRoom(null)
            }) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_back),
                    contentDescription = "Back"
                )
            }
        },

        actions = {
            if(currentDestination == "ContentReviewScreen"){
                IconButton(onClick = doPrint) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_print),
                        contentDescription = "Localized description"
                    )
                }
            }
            dropDownMenu(descriptionViewModel, navController, currentDestination, authViewModel, journalsViewModel, toWatchText, watchedText)
        }
    )
}

@Composable
fun dropDownMenu(descriptionViewModel: DescriptionViewModel, navController: NavController, currentDestination: String, authViewModel: AuthViewModel, journalsViewModel: JournalsViewModel, toWatchText:String, watchedText: String) {
    var expanded by remember { mutableStateOf(false) }
    var text = remember{ descriptionViewModel.uiState.movieToWatchStatus }

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                painter = painterResource(Res.drawable.ic_more_vert),
                contentDescription = stringResource(Res.string.expand)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = {Text(toWatchText)},
                onClick = {
                    val movieId = if(descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo!!.id else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id
                    val movieForRetrofit = if (descriptionViewModel.uiState.movieInfo != null) RoomMovieInfoForRetrofit(movieId!!) else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!
                    if(!descriptionViewModel.uiState.movieToWatchStatus){
                        descriptionViewModel.addMovieToDB(movieForRetrofit)
                        descriptionViewModel.addMovieToWatchToDB(
                            MoviesToWatchForRetrofit(
                                user = User(
                                    id = authViewModel.uiState.user!!.id
                                ), movie = movieForRetrofit
                            )
                        )
                        if(!descriptionViewModel.uiState.watchedMovieStatus){
                            if(descriptionViewModel.uiState.movieInfo != null){
                                var movie = descriptionViewModel.uiState.movieInfo!!
                                journalsViewModel.addMovieToLocalDB(
                                    RoomMovieInfo(
                                        id = movie.id!!,
                                        name = movie.name,
                                        feesWorld = movie.fees?.world?.value ?: 0,
                                        feesUsa = movie.fees?.usa?.value ?: 0,
                                        budget = movie.budget?.value ?: 0,
                                        posterUrl = movie.poster?.url ?: "",
                                        worldPremier = movie.premiere?.world ?: "",
                                        russiaPremier = movie.premiere?.russia ?: "",
                                        kpRating = movie.rating?.kp ?: 0.0,
                                        imdbRating = movie.rating?.imdb ?: 0.0,
                                        movieLength = movie.movieLength ?: 0,
                                        type = movie.type,
                                        typeNumber = movie.typeNumber ?: 0,
                                        description = movie.description,
                                        year = movie.year ?: 0,
                                        alternativeName = movie.alternativeName,
                                        enName = movie.enName,
                                        ageRating = movie.ageRating.toString(),
                                        isSeries = movie.isSeries,
                                        seriesLength = movie.seriesLength.toString(),
                                        totalSeriesLength = movie.totalSeriesLength?.toString()
                                    )
                                )
                                journalsViewModel.writeCountriesToLocalDB(movie.countries, movie.id!!)
                                journalsViewModel.writeGenresToLocalDB(movie.genres, movie.id!!)
                                journalsViewModel.writePersonsToLocalDB(movie.persons, movie.id!!)
                                journalsViewModel.writeSeasonsInfoToLocalDB(movie.seasonsInfo, movie.id!!)
                            }else {
                                var movie = descriptionViewModel.uiState.roomMovieInfoForRetrofit!!
                                journalsViewModel.addMovieToLocalDB(RoomMovieInfo(id = movie.id!!, name = movie.name, feesWorld = movie.feesWorld?:0, feesUsa = movie.feesUsa?:0,
                                    budget = movie.budget?:0, posterUrl = movie.posterUrl?:"", worldPremier = movie.worldPremier?:"", russiaPremier = movie.russiaPremier?:"",
                                    kpRating = movie.kpRating?:0.0, imdbRating = movie.imdbRating?:0.0, movieLength = movie.movieLength?:0, type = movie.type,
                                    typeNumber = movie.typeNumber?:0, description = movie.description, year = movie.year?:0, alternativeName = movie.alternativeName,
                                    enName = movie.enName, ageRating = movie.ageRating.toString(), isSeries = movie.isSeries, seriesLength = movie.seriesLength,
                                    totalSeriesLength = movie.totalSeriesLength))
                            }

                        }
                        journalsViewModel.addMovieToWatchToLocalDB(
                            MoviesToWatch(
                                userId = authViewModel.uiState.user!!.id,
                                movieId = movieId!!
                            )
                        )
                    }else {
                        val movieId = if(descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo!!.id else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id
                        descriptionViewModel.deleteMovieToWatchFromDB(MoviesToWatchForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = movieForRetrofit))
                        descriptionViewModel.deleteMovieToWatchFromLocalDB(authViewModel.uiState.user!!.id, movieId!!)
                        if(!descriptionViewModel.uiState.watchedMovieStatus){
                            journalsViewModel.deleteMovieByIdFromLocalDB(movieId!!)
                        }
                        if(descriptionViewModel.uiState.movieInfo == null){
                            navController.navigateUp()
                        }
                    }
                    descriptionViewModel.checkMovieToWatch(movieId ?: 0, authViewModel.uiState.user?.id?:0)
                }
            )
            DropdownMenuItem(
                text = { Text(watchedText)},
                onClick = {
                    val movieId = if(descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo!!.id else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id
                    val movieForRetrofit = if (descriptionViewModel.uiState.movieInfo != null) RoomMovieInfoForRetrofit(movieId!!) else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!
                    if(!descriptionViewModel.uiState.watchedMovieStatus){
                        descriptionViewModel.addMovieToDB(movieForRetrofit)
                        descriptionViewModel.addWatchedMovieToDB(
                            WatchedMoviesForRetrofit(
                                user = User(
                                    id = authViewModel.uiState.user!!.id
                                ), movie = movieForRetrofit
                            )
                        )
                        if(!descriptionViewModel.uiState.movieToWatchStatus){
                            if(descriptionViewModel.uiState.movieInfo != null){
                                var movie = descriptionViewModel.uiState.movieInfo!!
                                journalsViewModel.addMovieToLocalDB(RoomMovieInfo(id = movie.id!!, name = movie.name, feesWorld = movie.fees?.world?.value?:0, feesUsa = movie.fees?.usa?.value?:0,
                                    budget = movie.budget?.value?:0, posterUrl = movie.poster?.url?:"", worldPremier = movie.premiere?.world?:"", russiaPremier = movie.premiere?.russia?:"",
                                    kpRating = movie.rating?.kp?:0.0, imdbRating = movie.rating?.imdb?:0.0, movieLength = movie.movieLength?:0, type = movie.type,
                                    typeNumber = movie.typeNumber?:0, description = movie.description, year = movie.year?:0, alternativeName = movie.alternativeName,
                                    enName = movie.enName, ageRating = movie.ageRating.toString(), isSeries = movie.isSeries, seriesLength = movie.seriesLength.toString(),
                                    totalSeriesLength = movie.totalSeriesLength?.toString()))
                                journalsViewModel.writeCountriesToLocalDB(movie.countries, movie.id!!)
                                journalsViewModel.writeGenresToLocalDB(movie.genres, movie.id!!)
                                journalsViewModel.writePersonsToLocalDB(movie.persons, movie.id!!)
                                journalsViewModel.writeSeasonsInfoToLocalDB(movie.seasonsInfo, movie.id!!)
                            }else {
                                var movie = descriptionViewModel.uiState.roomMovieInfoForRetrofit!!
                                journalsViewModel.addMovieToLocalDB(RoomMovieInfo(id = movie.id!!, name = movie.name, feesWorld = movie.feesWorld?:0, feesUsa = movie.feesUsa?:0,
                                    budget = movie.budget?:0, posterUrl = movie.posterUrl?:"", worldPremier = movie.worldPremier?:"", russiaPremier = movie.russiaPremier?:"",
                                    kpRating = movie.kpRating?:0.0, imdbRating = movie.imdbRating?:0.0, movieLength = movie.movieLength?:0, type = movie.type,
                                    typeNumber = movie.typeNumber?:0, description = movie.description, year = movie.year?:0, alternativeName = movie.alternativeName,
                                    enName = movie.enName, ageRating = movie.ageRating.toString(), isSeries = movie.isSeries, seriesLength = movie.seriesLength,
                                    totalSeriesLength = movie.totalSeriesLength))
                            }

                        }
                        journalsViewModel.addWatchedMovieToLocalDB(
                            WatchedMovies(
                                userId = authViewModel.uiState.user!!.id,
                                movieId = movieId ?: 0
                            )
                        )
                    }else {
                        val movieId = if(descriptionViewModel.uiState.movieInfo != null) descriptionViewModel.uiState.movieInfo!!.id else descriptionViewModel.uiState.roomMovieInfoForRetrofit!!.id
                        descriptionViewModel.deleteWatchedMovieFromDB(WatchedMoviesForRetrofit(user = User(id = authViewModel.uiState.user!!.id), movie = movieForRetrofit))
                        descriptionViewModel.deleteWatchedMovieFromLocalDB(authViewModel.uiState.user!!.id, movieId!!)
                        if(!descriptionViewModel.uiState.movieToWatchStatus){
                            journalsViewModel.deleteMovieByIdFromLocalDB(movieId)
                        }
                        if(descriptionViewModel.uiState.movieInfo == null){
                            navController.navigateUp()
                        }
                    }
                    descriptionViewModel.checkWatchedMovie(movieId ?: 0, authViewModel.uiState.user?.id?:0)
                }
            )
            if(descriptionViewModel.uiState.movieToWatchStatus && currentDestination =="LocalContentDescriptionScreen"){
                DropdownMenuItem(
                    text = { Text(stringResource(Res.string.remind_to_watch))},
                    onClick = {
                        descriptionViewModel.updateReminderDialogueStatus(true)
                    }
                )
            }
        }
    }
}

/*fun createHtml(reviewViewModel: ReviewViewModel, review: String, rating: String, likes: String, dislikes: String, notes: String): String{
    return buildString {
        appendHTML().html {
            head {
                title {  +"${reviewViewModel.uiState.roomMovieInfoForRetrofit?.name?:"ревью на фильм"}" }
            }
            body {
                h1 { +"${review}: ${reviewViewModel.uiState.roomMovieInfoForRetrofit?.name}" }
                p {
                    em { +"${rating}: "}
                    +"${reviewViewModel.uiState.rating}"
                }
                var likes = ""
                reviewViewModel.uiState.likesForReview?.forEach {
                    likes = "$likes${it.description}, "
                }
                var dislikes = ""
                reviewViewModel.uiState.dislikesForReview?.forEach {
                    dislikes = "$dislikes${it.description}, "
                }
                p {
                    em { +"${likes}: "}
                    +"$likes"
                }
                p {
                    em { +"${dislikes}: "}
                    +"$dislikes"
                }
                p {
                    em { +"${notes}: "}
                    +"${reviewViewModel.uiState.reviewText}"
                }
            }
        }
    }
}

fun createPDFFromHtml(html: String, context: Context, name: String): File? {


    val outputStream = ByteArrayOutputStream()

    HtmlConverter.convertToPdf(html, outputStream)

    val pdfFile = File(context.cacheDir, "Ревью_на_фильм_${name}.pdf")

    FileOutputStream(pdfFile).use { fileOutputStream ->
        outputStream.writeTo(fileOutputStream)
    }

    return pdfFile
}

fun savePDF(pdfFile: File?, context: Context){

    if(pdfFile!=null){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        val pdfUri = FileProvider.getUriForFile(context, context.packageName + ".provider", pdfFile)
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        ContextCompat.startActivity(
            context,
            Intent.createChooser(intent, "Share PDF with"),
            null
        )
    }


}*/