package com.github.rodionk77.cinemajournalkmp.presentation.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.no_name
import cinemajournalkmp.composeapp.generated.resources.poster_placeholder
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.github.rodionk77.cinemajournalkmp.data.models.MovieInfo
import org.jetbrains.compose.resources.stringResource

@Composable
fun cinemaItemRow(item: MovieInfo, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel, navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp)
            .width(160.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .clickable {
                if (navController.currentDestination?.route == "GalleryScreen"){
                    galleryViewModel.selectMovie(item.id!!)
                    descriptionViewModel.refreshCurrentMovieInfo(item)
                    descriptionViewModel.refreshCurrentMovieInfoRoom(null)
                    descriptionViewModel.getMovieInfo(item.id!!)
                    navController.navigate("ContentDescriptionScreen")
                }
                else if (navController.currentDestination?.route == "JournalsScreen"){
                    galleryViewModel.selectMovie(item.id!!)
                    descriptionViewModel.refreshCurrentMovieInfo(item)
                    descriptionViewModel.refreshCurrentMovieInfoRoom(null)
                    descriptionViewModel.getMovieInfo(item.id!!)
                    navController.navigate("ContentReviewScreen")
                }

            }
            //.background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        AsyncImage(
            model = item.poster?.url,
            contentDescription = "",
            placeholder = painterResource(Res.drawable.poster_placeholder),
            error = painterResource(Res.drawable.poster_placeholder),
            modifier = Modifier
                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp, top = 8.dp)
                .height(164.dp),
        )
        /*Image(
            painter = AsyncImage(model = item.poster?.url, contentDescription = "image") /*painterResource(id = R.drawable.poster)*/,
            contentDescription = "Poster of cinema",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp, top = 8.dp)
                .height(164.dp)
                //.clip(CircleShape)
        )*/
        Text(
            text = item.name ?: stringResource(Res.string.no_name),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 4.dp),
            letterSpacing= (-0.8).sp,
            minLines = 3,
            maxLines = 3,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

//@Preview
//@Composable
//fun CinemaItemPreview() {
//    AppTheme {
//        cinemaItemRow(CinemaRowModel(imageId = R.drawable.poster, title = "Железный человек"), rememberNavController())
//    }
//}