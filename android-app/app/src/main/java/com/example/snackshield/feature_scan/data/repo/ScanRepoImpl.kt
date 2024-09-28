package com.example.snackshield.feature_scan.data.repo

import android.util.Log
import com.example.snackshield.feature_scan.data.network.ScanApi
import com.example.snackshield.feature_scan.domain.model.Barcode
import com.example.snackshield.feature_scan.domain.model.DataFromBarcode
import com.example.snackshield.feature_scan.domain.model.DetectionData
import com.example.snackshield.feature_scan.domain.model.NutrientRequest
import com.example.snackshield.feature_scan.domain.model.RecipeData
import com.example.snackshield.feature_scan.domain.repo.ScanRepo
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class ScanRepoImpl @Inject constructor(
    private val scanApi: ScanApi
) : ScanRepo {
    override suspend fun barcode(barcode: String): DataFromBarcode? {
        val productDataDto = scanApi.barcode(Barcode(barcode).toBarcodeDto())
        return if (productDataDto.isSuccessful) {
            productDataDto.body()!!.data!!.toData()
        } else {
            null
        }
    }

    override suspend fun recipeRecommend(nutrientRequest: NutrientRequest): RecipeData? {
        val recipeDataDto = scanApi.recommendation(nutrientRequest.toNutrientRequestDto())
        return if (recipeDataDto.isSuccessful) {
            recipeDataDto.body()!!.data!!.toRecipeData()
        } else {
            null
        }
    }

    override suspend fun uploadImageForDetection(userId: String, imageFile: File): DetectionData? {
        val requestFile = imageFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("file", imageFile.name, requestFile)
        val userIdRequestBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())

        val response = scanApi.uploadImageForDetection(body)
        return if (response.isSuccessful) {
            response.body()!!.data!!.toDetection()
        } else {
            null
        }
    }
}