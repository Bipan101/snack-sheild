package com.example.snackshield.feature_scan.presentation

import android.content.Context
import android.net.Uri
import com.example.snackshield.feature_scan.domain.model.IngredientData
import com.example.snackshield.feature_scan.domain.model.NutrientRequest

sealed interface ScanEvent {
    data class GetDataFromBarcode(val barcode : String) : ScanEvent
    data class Recommend(val nutrientRequest: NutrientRequest) : ScanEvent
    data class DetectFromImage(val imageUri: Uri, val context: Context) : ScanEvent
    data class SaveImage(val imageUri: Uri) : ScanEvent
    data class DetectFromLabel(val ingredientData: String) : ScanEvent
}