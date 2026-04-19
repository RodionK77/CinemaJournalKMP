package com.github.rodionk77.cinemajournalkmp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.account_info
import cinemajournalkmp.composeapp.generated.resources.common_account
import cinemajournalkmp.composeapp.generated.resources.empty_viewed_journal
import cinemajournalkmp.composeapp.generated.resources.empty_wanted_journal
import cinemajournalkmp.composeapp.generated.resources.exit_from_account
import cinemajournalkmp.composeapp.generated.resources.kid_account
import cinemajournalkmp.composeapp.generated.resources.understand
import cinemajournalkmp.composeapp.generated.resources.viewed
import cinemajournalkmp.composeapp.generated.resources.want_to_see
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import com.github.rodionk77.cinemajournalkmp.presentation.items.cinemaItemColumnLocalDB
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalsScreen(navController: NavController, journalsViewModel: JournalsViewModel, reviewViewModel: ReviewViewModel, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel, authViewModel: AuthViewModel) {

    reviewViewModel.changeLikes(null)
    reviewViewModel.changeDislikes(null)
    reviewViewModel.changeReviewText(null)
    reviewViewModel.changeRating(null)
    reviewViewModel.changeDate(null)
    descriptionViewModel.changeTimeToWatch(null, null)
    descriptionViewModel.changeDateToWatch(null)

    if(journalsViewModel.uiState.accountDialogState){
        dialog(navController = navController, journalsViewModel = journalsViewModel, authViewModel = authViewModel)
    }


    Column(
        modifier = Modifier
            .fillMaxSize(),

    ) {
        TabRow(
            selectedTabIndex = journalsViewModel.uiState.selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.primary,
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[journalsViewModel.uiState.selectedTabIndex]),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        ) {
            Tab(
                text = { Text(
                    text = stringResource(Res.string.viewed),
                    fontSize = 18.sp
                ) },
                selected = journalsViewModel.uiState.selectedTabIndex == 0,
                onClick = {
                    journalsViewModel.changeSelectedTabIndex(0)
                    //selectedTabIndex = 0
                }
            )
            Tab(
                text = { Text(
                    text = stringResource(Res.string.want_to_see),
                    fontSize = 18.sp
                ) },
                selected = journalsViewModel.uiState.selectedTabIndex == 1,
                onClick = {
                    journalsViewModel.changeSelectedTabIndex(1)
                    //selectedTabIndex = 1
                }
            )
        }

        PullToRefreshBox(
            isRefreshing = journalsViewModel.uiState.isRefreshing,
            onRefresh = { journalsViewModel.refreshData() },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp),
            ) {

                if(journalsViewModel.uiState.selectedTabIndex == 0) {
                    journalsViewModel.getMoviesFromWatchedFromLocalDB()
                    val watchedMovies = journalsViewModel.uiState.watchedMovies
                    if(!watchedMovies.isNullOrEmpty()){
                        LazyColumn(modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onSecondary),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            itemsIndexed(watchedMovies){ _, item ->
                                cinemaItemColumnLocalDB(
                                    item = item, navController = navController, selectedIndex = journalsViewModel.uiState.selectedTabIndex,
                                    reviewViewModel = reviewViewModel, galleryViewModel = galleryViewModel,
                                    descriptionViewModel = descriptionViewModel
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(stringResource(Res.string.empty_viewed_journal))
                        }
                    }
                }else if (journalsViewModel.uiState.selectedTabIndex == 1) {
                    journalsViewModel.getMoviesFromToWatchFromLocalDB()
                    val moviesToWatch = journalsViewModel.uiState.moviesToWatch
                    if(!moviesToWatch.isNullOrEmpty()){
                        LazyColumn(modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.onSecondary),
                            horizontalAlignment = Alignment.CenterHorizontally){
                            itemsIndexed(moviesToWatch){ _, item ->
                                cinemaItemColumnLocalDB(
                                    item = item, navController = navController, selectedIndex = journalsViewModel.uiState.selectedTabIndex,
                                    reviewViewModel = reviewViewModel, galleryViewModel = galleryViewModel,
                                    descriptionViewModel = descriptionViewModel
                                )
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(stringResource(Res.string.empty_wanted_journal))
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun dialog(navController: NavController, journalsViewModel: JournalsViewModel, authViewModel: AuthViewModel) {
    Dialog(onDismissRequest = { journalsViewModel.changeAccountDialogState(false) }) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(Res.string.account_info),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                Text(
                    text = authViewModel.uiState.user?.email ?: "",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                val isKid = authViewModel.uiState.user?.role != 0
                Surface(
                    color = if (isKid) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.secondaryContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (isKid) stringResource(Res.string.kid_account) else stringResource(Res.string.common_account),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (isKid) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }

                Spacer(Modifier.height(4.dp))

                Button(
                    onClick = { journalsViewModel.changeAccountDialogState(false) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(Res.string.understand), fontSize = 15.sp)
                }

                TextButton(
                    onClick = {
                        journalsViewModel.changeAccountDialogState(false)
                        authViewModel.deleteUserById(authViewModel.uiState.user!!.id)
                        journalsViewModel.deleteAllMoviesFromLocalDB()
                        authViewModel.changeUser(null)
                        journalsViewModel.changeUser(null)
                        authViewModel.signOutUser()
                        navController.navigate("EntranceScreen") { popUpTo(0) }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(stringResource(Res.string.exit_from_account), fontSize = 15.sp)
                }
            }
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun JournalsPreview() {
//    AppTheme {
//        JournalsScreen(rememberNavController())
//    }
//}

