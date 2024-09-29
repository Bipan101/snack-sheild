package com.example.snackshield.feature_scan.domain.repo

import com.example.snackshield.feature_scan.domain.model.DataFromBarcode
import com.example.snackshield.feature_scan.domain.model.DetectionData
import com.example.snackshield.feature_scan.domain.model.LabelData
import com.example.snackshield.feature_scan.domain.model.NutrientRequest
import com.example.snackshield.feature_scan.domain.model.RecipeData
import java.io.File

interface ScanRepo {
    suspend fun barcode(barcode: String, userId: String): DataFromBarcode?
    suspend fun recipeRecommend(nutrientRequest: NutrientRequest): RecipeData?
    suspend fun uploadImageForDetection(userId: String, imageFile: File): DetectionData?
    suspend fun detectFromLabel(
        ingredients: String,
        userId: String
    ): LabelData?
}