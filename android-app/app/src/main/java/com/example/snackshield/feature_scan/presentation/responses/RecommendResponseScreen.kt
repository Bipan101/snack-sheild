package com.example.snackshield.feature_scan.presentation.responses

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.snackshield.feature_scan.presentation.ScanViewModel


@Composable
fun RecommendResponseScreen(scanViewModel: ScanViewModel,goBack : () -> Unit) {
    BackHandler {
        goBack()
        scanViewModel.resetState()
    }
    val state by scanViewModel.state.collectAsState()
    Log.d("Hello", "RecommendResponseScreen: ${state.recommendedProduct} ")
}