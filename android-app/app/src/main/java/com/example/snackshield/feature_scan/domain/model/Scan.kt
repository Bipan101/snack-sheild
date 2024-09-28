package com.example.snackshield.feature_scan.domain.model

import com.example.snackshield.feature_scan.data.model.BarcodeDto
import com.example.snackshield.feature_scan.data.model.NutrientInputDto
import com.example.snackshield.feature_scan.data.model.NutrientRequestDto
import kotlinx.serialization.Serializable

data class Barcode(
    val barcode : String
) {
    fun toBarcodeDto() = BarcodeDto(
        barcode = barcode
    )
}


data class ProductData(
    val data: DataFromBarcode?,
    val message: String?
)


data class DataFromBarcode(
    val productName: String?,
    val ingredientData: IngredientData?,
    val nutrientPercentages: NutrientPercentages?,
    val nutritionDataPerGm: String?
)


data class IngredientData(
    val ingredients: List<String>?,
    val allergens: List<String>?
)


data class NutrientPercentages(
    val carbohydrates_100g: Double?,
    val energy_kcal_100g: Double?,
    val energy_100g: Double?,
    val fat_100g: Double?,
    val fiber_100g: Double?,
    val fruits_vegetables_legumes_estimate_from_ingredients_100g: Int?,
    val fruits_vegetables_nuts_estimate_from_ingredients_100g: Int?,
    val nova_group_100g: Double?,
    val proteins_100g: Double?,
    val salt_100g: Double?,
    val saturated_fat_100g: Double?,
    val sodium_100g: Double?,
    val sugars_100g: Double?
)

//Recommend
data class NutrientRequest(
    val nutrientInput: NutrientInput,
    val ingredients: List<String>,
) {
    fun toNutrientRequestDto() = NutrientRequestDto(
        nutrientInput = nutrientInput.toNutrientInputDto(),
        ingredients = ingredients
    )
}
data class NutrientInput(
    val calories: Float,
    val fatContent: Float,
    val saturatedFatContent: Float,
    val cholesterolContent: Float,
    val carbohydrateContent: Float,
    val fiberContent: Float,
    val sugarContent: Float,
    val proteinContent: Float
) {
    fun toNutrientInputDto() = NutrientInputDto(
        calories, fatContent, saturatedFatContent, cholesterolContent, carbohydrateContent, fiberContent, sugarContent, proteinContent
    )
}


data class RecipeData(
    val output: List<Recipe>?
)

data class Recipe(
    val Name: String?,
    val CookTime: String?,
    val PrepTime: String?,
    val TotalTime: String?,
    val RecipeIngredientParts: List<String>?,
    val Calories: Float?,
    val FatContent: Float?,
    val SaturatedFatContent: Float?,
    val CholesterolContent: Float?,
    val SodiumContent: Float?,
    val CarbohydrateContent: Float?,
    val FiberContent: Float?,
    val SugarContent: Float?,
    val ProteinContent: Float?,
    val RecipeInstructions: List<String>?
)

//Image
data class DetectionData(
    val inference_id: String?,
    val time: Double?,
    val image: ImageDetails?,
    val predictions: List<Prediction>?
)

@Serializable
data class ImageDetails(
    val width: Int,
    val height: Int
)

@Serializable
data class Prediction(
    val x: Float?,
    val y: Float?,
    val width: Float?,
    val height: Float?,
    val confidence: Float?,
    val `class`: String?,
    val class_id: Int?,
    val detection_id: String?
)