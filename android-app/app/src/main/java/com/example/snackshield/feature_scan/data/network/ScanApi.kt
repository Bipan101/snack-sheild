package com.example.snackshield.feature_scan.data.network

import com.example.snackshield.feature_scan.data.model.BarcodeDto
import com.example.snackshield.feature_scan.data.model.ImageAllergenDetectionResponseDto
import com.example.snackshield.feature_scan.data.model.NutrientRequestDto
import com.example.snackshield.feature_scan.data.model.ProductDataDto
import com.example.snackshield.feature_scan.data.model.RecipeResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ScanApi {
@POST("barcode")
suspend fun barcode(@Body barcode : BarcodeDto) : Response<ProductDataDto>

@POST("recommendation")
suspend fun recommendation(@Body nutrientRequestDto: NutrientRequestDto) : Response<RecipeResponseDto>

    @Multipart
    @POST("allergen/image")
   suspend fun uploadImageForDetection(
        @Part file: MultipartBody.Part          // File to upload
    ): Response<ImageAllergenDetectionResponseDto>
}