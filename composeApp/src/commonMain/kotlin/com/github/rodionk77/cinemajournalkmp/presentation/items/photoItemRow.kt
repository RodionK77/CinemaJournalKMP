package com.github.rodionk77.cinemajournalkmp.presentation.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import cinemajournalkmp.composeapp.generated.resources.Res
import cinemajournalkmp.composeapp.generated.resources.photo
import com.github.rodionk77.cinemajournalkmp.data.models.Persons
import com.github.rodionk77.cinemajournalkmp.data.models.RoomModels.PersonsForRetrofit
import org.jetbrains.compose.resources.stringResource

@Composable
fun photoItemRow(item1: Persons?, item2: PersonsForRetrofit?) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp, end = 12.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        var photo = ""
        var name = ""
        if(item1 == null){
            photo = item2?.photo?:""
            name = item2?.name?:""
        }else {
            photo = item1.photo?:""
            name = item1.name?:""
        }
        AsyncImage(
            model = photo,
            contentDescription = stringResource(Res.string.photo),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp, top = 4.dp)
                .size(64.dp)
                .clip(CircleShape)
        )
        Text(text = name, modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 4.dp) )
    }
}

//@Preview
//@Composable
//fun PhotoItemPreview() {
//    AppTheme {
//        photoItemRow(ItemRowModel(imageId = R.drawable.jon, title = "Джон Фавро"))
//    }
//}