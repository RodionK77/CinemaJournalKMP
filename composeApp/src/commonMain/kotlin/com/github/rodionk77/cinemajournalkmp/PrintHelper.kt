package com.github.rodionk77.cinemajournalkmp

import androidx.compose.runtime.Composable
import com.example.cinemajournal.ui.theme.screens.viewmodels.ReviewViewModel

@Composable
expect fun rememberPrintAction(html: String, title: String): () -> Unit

fun createReviewHtml(
    reviewViewModel: ReviewViewModel,
    reviewLabel: String,
    ratingLabel: String,
    likesLabel: String,
    dislikesLabel: String,
    notesLabel: String
): String {
    val movie = reviewViewModel.uiState.roomMovieInfoForRetrofit
    val likesStr = reviewViewModel.uiState.likesForReview
        ?.mapNotNull { it.description }
        ?.filter { it.isNotBlank() }
        ?.joinToString(", ") ?: ""
    val dislikesStr = reviewViewModel.uiState.dislikesForReview
        ?.mapNotNull { it.description }
        ?.filter { it.isNotBlank() }
        ?.joinToString(", ") ?: ""

    return buildString {
        append("<!DOCTYPE html><html><head>")
        append("<meta charset=\"UTF-8\">")
        append("<title>${movie?.name ?: "Ревью"}</title>")
        append("<style>")
        append("body{font-family:sans-serif;padding:20px;max-width:640px;margin:0 auto;}")
        append("h1{font-size:22px;margin-bottom:16px;}")
        append("p{margin:8px 0;line-height:1.5;}")
        append("em{color:#555;font-style:normal;font-weight:bold;}")
        append("</style></head><body>")
        append("<h1>$reviewLabel: ${movie?.name ?: ""}</h1>")
        append("<p><em>$ratingLabel:</em> ${reviewViewModel.uiState.rating ?: 0.0f}</p>")
        if (reviewViewModel.uiState.dateWatched != null)
            append("<p><em>Дата просмотра:</em> ${reviewViewModel.uiState.dateWatched}</p>")
        if (likesStr.isNotBlank())
            append("<p><em>$likesLabel:</em> $likesStr</p>")
        if (dislikesStr.isNotBlank())
            append("<p><em>$dislikesLabel:</em> $dislikesStr</p>")
        if (!reviewViewModel.uiState.reviewText.isNullOrBlank())
            append("<p><em>$notesLabel:</em><br>${reviewViewModel.uiState.reviewText!!.replace("\n", "<br>")}</p>")
        append("</body></html>")
    }
}
