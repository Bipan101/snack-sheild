package com.example.snackshield.feature_scan.presentation

import com.example.snackshield.feature_scan.domain.model.DataFromBarcode
import com.example.snackshield.feature_scan.domain.model.DetectionData
import com.example.snackshield.feature_scan.domain.model.RecipeData

sealed interface ScanUiState {
    data object Remaining : ScanUiState
    data object Loading : ScanUiState
    data object Success : ScanUiState
    data class Error(val error: String) : ScanUiState
}

data class ScanState(
    val productFormBarcode: DataFromBarcode? = null,
    val recommendedProduct: RecipeData? = null,
    val detectFromImage : DetectionData? = null
)