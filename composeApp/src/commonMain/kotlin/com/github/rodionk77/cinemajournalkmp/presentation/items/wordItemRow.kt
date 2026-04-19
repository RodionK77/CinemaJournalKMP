package com.github.rodionk77.cinemajournalkmp.presentation.items

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun wordItemRow(text: String) {


    Card(
        modifier = Modifier.padding(top = 6.dp, bottom = 6.dp, end = 12.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            text = text,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

    /*Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 6.dp, bottom = 6.dp, end = 12.dp)
            .clip(shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp))
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        Image(
            painter = painterResource(id = item.imageId),
            contentDescription = "Image of man",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 2.dp, start = 2.dp, end = 2.dp, top = 4.dp)
                .size(64.dp)
                .clip(CircleShape)
        )
        Text(text = item.title, modifier = Modifier.padding(start = 6.dp, end = 6.dp, bottom = 4.dp) )
    }*/
}
