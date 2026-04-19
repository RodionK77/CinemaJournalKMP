package com.github.rodionk77.cinemajournalkmp.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.github.rodionk77.cinemajournalkmp.isOnline
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cinemajournal.ui.theme.screens.viewmodels.AuthViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.JournalsViewModel
import io.github.aakira.napier.Napier
import org.koin.compose.viewmodel.koinViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.gallery
import cinemajournalkmp.composeapp.generated.resources.ic_article
import cinemajournalkmp.composeapp.generated.resources.ic_batch
import cinemajournalkmp.composeapp.generated.resources.ic_manage_search
import cinemajournalkmp.composeapp.generated.resources.journals
import cinemajournalkmp.composeapp.generated.resources.recommendations
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import com.example.cinemajournal.ui.theme.screens.viewmodels.AdditionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.RecommendationsViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel
import com.github.rodionk77.cinemajournalkmp.presentation.AdditionToolbar
import com.github.rodionk77.cinemajournalkmp.presentation.ContentToolbar
import com.github.rodionk77.cinemajournalkmp.presentation.GalleryToolbar
import com.github.rodionk77.cinemajournalkmp.presentation.JournalsToolbar
import com.github.rodionk77.cinemajournalkmp.presentation.RecommendationsToolbar
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen (){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val topBarState = rememberSaveable { (mutableStateOf(0)) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val authViewModel = koinViewModel<AuthViewModel>()
    val galleryViewModel = koinViewModel<GalleryViewModel>()
    val journalsViewModel = koinViewModel<JournalsViewModel>()
    val descriptionViewModel = koinViewModel<DescriptionViewModel>()
    val reviewViewModel = koinViewModel<ReviewViewModel>()
    val additionViewModel = koinViewModel<AdditionViewModel>()
    val recommendationsViewModel = koinViewModel<RecommendationsViewModel>()

    LaunchedEffect(Unit) {
        galleryViewModel.refreshTops(0)
    }

    if(currentDestination == "EntranceScreen"){
        if(!authViewModel.uiState.isRefreshLoginProcess){
            authViewModel.getAllUser()
        }
        if(authViewModel.uiState.user != null){
            if(!isOnline()){
                galleryViewModel.refreshTops(authViewModel.uiState.user?.role?:0)
                navController.navigate("JournalsScreen"){
                    popUpTo(0)
                    Napier.d("${journalsViewModel.uiState.user}", tag = "MainScreen")
                }
            }else {
                journalsViewModel.getUserFromDB(authViewModel.uiState.user!!.id)
                Napier.d("проверяем вход\n${authViewModel.uiState.user}\n${journalsViewModel.uiState.user}", tag = "MainScreen")
                if(journalsViewModel.uiState.user != null){
                    journalsViewModel.startUpdateLocalDB(journalsViewModel.uiState.user!!)
                    galleryViewModel.refreshTops(authViewModel.uiState.user?.role?:0)
                    navController.navigate("JournalsScreen"){
                        popUpTo(0)
                        Napier.d("${journalsViewModel.uiState.user}", tag = "MainScreen")
                    }
                }
            }
        }

    }

    var startDestination = "EntranceScreen"

    when (navBackStackEntry?.destination?.route) {
        "JournalsScreen" -> {
            topBarState.value = 0
        }
        "GalleryScreen" -> {
            topBarState.value = 1
        }
        "RecommendationsScreen" -> {
            topBarState.value = 5
        }
        "ContentDescriptionScreen", "LocalContentDescriptionScreen", "ContentReviewScreen" -> {
            topBarState.value = 2
        }
        "ContentAdditionScreen" -> {
            topBarState.value = 3
        }
        else -> {
            topBarState.value = 4
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection).imePadding(),
        topBar = { when(topBarState.value){
            0-> JournalsToolbar(scrollBehavior = scrollBehavior, navController = navController, journalsViewModel = journalsViewModel, authViewModel = authViewModel)
            1-> GalleryToolbar(scrollBehavior = scrollBehavior, galleryViewModel, authViewModel)
            2-> ContentToolbar(navController = navController, currentDestination?:"JournalsScreen", descriptionViewModel, reviewViewModel, authViewModel, journalsViewModel)
            3-> AdditionToolbar(scrollBehavior = scrollBehavior, navController = navController, additionViewModel = additionViewModel)
            5-> RecommendationsToolbar(scrollBehavior = scrollBehavior)
        } },
        bottomBar = {
            if(topBarState.value != 4)
                bottomBar(navController = navController, currentDestination = currentDestination, descriptionViewModel) },

        ){ innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            Column {
                NavHost(navController = navController, startDestination = startDestination) {
                    composable("EntranceScreen") {
                        EntranceScreen(navController, authViewModel, journalsViewModel, galleryViewModel)
                    }
                    composable("JournalsScreen") {
                        JournalsScreen(navController, journalsViewModel, reviewViewModel, galleryViewModel, descriptionViewModel, authViewModel)
                    }
                    composable("ContentAdditionScreen") {
                        ContentAdditionScreen(navController, additionViewModel, authViewModel)
                    }
                    composable("GalleryScreen") {
                        GalleryScreen(navController, galleryViewModel, descriptionViewModel, authViewModel)
                    }
                    composable("ContentDescriptionScreen") {
                        ContentDescriptionScreen(navController, descriptionViewModel, authViewModel, journalsViewModel)
                    }
                    composable("LocalContentDescriptionScreen") {
                        ContentDescriptionScreen(navController, descriptionViewModel, authViewModel, journalsViewModel)
                    }
                    composable("ContentReviewScreen") {
                        ContentReviewScreen(navController = navController, reviewViewModel, authViewModel)
                    }
                    composable("RecommendationsScreen") {
                        RecommendationsScreen(navController, recommendationsViewModel, authViewModel, galleryViewModel, descriptionViewModel)
                    }
                }
            }
        }
    }
}

data class BottomNavigationItem(
    val title: String,
    val route: List<String>,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource
)

@Composable
private fun bottomBar(navController: NavController, currentDestination: String?, descriptionViewModel: DescriptionViewModel){

    val navItems = listOf(
        BottomNavigationItem(
            title = stringResource(Res.string.journals),
            route = listOf("JournalsScreen", "ContentAdditionScreen", "ContentReviewScreen", "LocalContentDescriptionScreen"),
            selectedIcon = Res.drawable.ic_article,
            unselectedIcon = Res.drawable.ic_article,
        ),
        BottomNavigationItem(
            title = stringResource(Res.string.recommendations),
            route = listOf("RecommendationsScreen"),
            selectedIcon = Res.drawable.ic_batch,
            unselectedIcon = Res.drawable.ic_batch,
        ),
        BottomNavigationItem(
            title = stringResource(Res.string.gallery),
            route = listOf("GalleryScreen", "ContentDescriptionScreen"),
            selectedIcon = Res.drawable.ic_manage_search,
            unselectedIcon = Res.drawable.ic_manage_search
        )
    )

    val selectedItemIndex = navItems.indexOfFirst { it.route.contains(currentDestination)}

    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    if (selectedItemIndex != index) {
                        navController.navigate(item.route.first()) {
                            popUpTo("JournalsScreen") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (index == selectedItemIndex) item.selectedIcon
                            else item.unselectedIcon
                        ),
                        contentDescription = item.title
                    )
                },
                label = {Text(text=item.title)}
            )
        }
    }

}

