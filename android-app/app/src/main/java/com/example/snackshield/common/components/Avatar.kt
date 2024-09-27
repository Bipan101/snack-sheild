package com.example.snackshield.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun Avatar(image: String, size: Int) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(size.dp),
            model = image,
            contentDescription = "image",
            contentScale = ContentScale.Crop
        )
    }
}