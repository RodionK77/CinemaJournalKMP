package com.github.rodionk77.cinemajournalkmp.presentation.items

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.no_name
import cinemajournalkmp.composeapp.generated.resources.poster_not_loaded
import cinemajournalkmp.composeapp.generated.resources.poster_placeholder
import com.example.cinemajournal.ui.theme.screens.viewmodels.DescriptionViewModel
import com.example.cinemajournal.ui.theme.screens.viewmodels.GalleryViewModel
import com.github.rodionk77.cinemajournalkmp.data.models.MovieInfo
import coil3.compose.AsyncImage
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun cinemaItemColumn(item: MovieInfo, navController: NavController, galleryViewModel: GalleryViewModel, descriptionViewModel: DescriptionViewModel) {

    val rating = item.rating?.kp ?: 0.0
    val ratingRounded = kotlin.math.round(rating * 10).toInt()
    val ratingStr = "${ratingRounded / 10}.${ratingRounded % 10}"
    val ratingColor = when {
        rating >= 7.0 -> Color(0xFF2E7D32)
        rating >= 5.0 -> Color(0xFFF57F17)
        else          -> Color(0xFFC62828)
    }

    val subtitle = when {
        !item.enName.isNullOrBlank() -> item.enName!!
        !item.alternativeName.isNullOrBlank() -> item.alternativeName!!
        else -> null
    }
    val genres = item.genres.joinToString(", ") { it.name ?: "" }.ifBlank { null }
    val countries = item.countries.joinToString(", ") { it.name ?: "" }.ifBlank { null }

    Card(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outlineVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(9.dp))
                .padding(2.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (navController.currentDestination?.route == "GalleryScreen"){
                            galleryViewModel.selectMovie(item.id!!)
                            descriptionViewModel.refreshCurrentMovieInfoRoom(null)
                            descriptionViewModel.refreshCurrentMovieInfo(item)
                            descriptionViewModel.getMovieInfo(item.id!!)
                            navController.navigate("ContentDescriptionScreen")
                        }
                        /*else if (navController.currentDestination?.route == "JournalsScreen"){
                            galleryViewModel.selectMovie(item.id!!)
                            descriptionViewModel.refreshCurrentMovieInfoRoom(null)
                            descriptionViewModel.refreshCurrentMovieInfo(item)
                            navController.navigate("ContentReviewScreen")
                        }*/
                    },
                shape = RoundedCornerShape(7.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    AsyncImage(
                        model = item.poster?.url,
                        contentDescription = stringResource(Res.string.poster_not_loaded),
                        placeholder = painterResource(Res.drawable.poster_placeholder),
                        error = painterResource(Res.drawable.poster_placeholder),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp)
                            .height(112.dp)
                    )
                    Column(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = item.name ?: stringResource(Res.string.no_name),
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        if (subtitle != null) {
                            Text(
                                text = subtitle,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (countries != null) {
                            Text(
                                text = countries,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        if (genres != null) {
                            Text(
                                text = genres,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 11.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(ratingColor)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "★  $ratingStr",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }

}
