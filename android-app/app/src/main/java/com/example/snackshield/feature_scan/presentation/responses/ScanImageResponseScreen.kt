package com.example.snackshield.feature_scan.presentation.responses

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.snackshield.common.components.AppTopBar
import com.example.snackshield.common.components.EmptyScreen
import com.example.snackshield.common.components.FoodStatus
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.feature_scan.presentation.ScanViewModel

@Composable
fun ScanImageResponseScreen(scanViewModel: ScanViewModel, goBack : () -> Unit) {
    BackHandler {
        goBack()
    }
    val state by scanViewModel.state.collectAsState()
    Log.d("Hello", "ScanImageResponseScreen: ${state.detectFromImage} ")
    if (state.detectFromImage != null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            AppTopBar(identifier = "Scan image response") {
                goBack()
            }
            Image(
                painter = rememberAsyncImagePainter(model = state.imageUri),
                contentDescription = "image",
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacing(height = 24)
            FoodStatus(status = state.detectFromImage!!.isSafeForUser)
        }
    } else {
        EmptyScreen()
    }
}