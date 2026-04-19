package com.github.rodionk77.cinemajournalkmp.presentation.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.season
import cinemajournalkmp.composeapp.generated.resources.series
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.SeasonsInfoForRetrofit
import com.github.rodionk77.cinemajournalkmp.data.models.SeasonsInfo
import org.jetbrains.compose.resources.stringResource

@Composable
fun seriesItemRow(item1: SeasonsInfo?, item2: SeasonsInfoForRetrofit?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp, end = 12.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        var number = 0
        var episodesCount = 0
        if(item1 == null){
            number = item2!!.number
            episodesCount = item2!!.episodesCount
        } else {
            number = item1!!.number?:0
            episodesCount = item1!!.episodesCount?:0
        }
        Text(text = "${stringResource(Res.string.season)}: $number", modifier = Modifier.padding(start = 6.dp, end = 6.dp, top = 4.dp), fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(text = "${stringResource(Res.string.series)}: $episodesCount", modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 4.dp), fontSize = 16.sp, )
    }
}

//@Preview
//@Composable
//fun PhotoItemPreview() {
//    AppTheme {
//        photoItemRow(ItemRowModel(imageId = R.drawable.jon, title = "Джон Фавро"))
//    }
//}