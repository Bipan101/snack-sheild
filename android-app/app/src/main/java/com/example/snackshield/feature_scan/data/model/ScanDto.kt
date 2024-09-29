package com.example.snackshield.feature_scan.data.model

import com.example.snackshield.feature_scan.domain.model.DataFromBarcode
import com.example.snackshield.feature_scan.domain.model.DetectionData
import com.example.snackshield.feature_scan.domain.model.ImageDetails
import com.example.snackshield.feature_scan.domain.model.IngredientData
import com.example.snackshield.feature_scan.domain.model.LabelData
import com.example.snackshield.feature_scan.domain.model.NutrientPercentages
import com.example.snackshield.feature_scan.domain.model.Prediction
import com.example.snackshield.feature_scan.domain.model.Recipe
import com.example.snackshield.feature_scan.domain.model.RecipeData
import kotlinx.serialization.Serializable

//Barcode
data class BarcodeDto(
    val barcode: String,
    val userId : String
)

@Serializable
data class ProductDataDto(
    val data: DataFromBarcodeDto?,
    val message: String?
)

@Serializable
data class DataFromBarcodeDto(
    val productName: String?,
    val ingredientData: IngredientDataDto?,
    val nutrientPercentages: NutrientPercentagesDto?,
    val nutritionDataPerGm: String?,
    val isSafeForUser : Boolean,
) {
    fun toData() = DataFromBarcode(
        productName = productName,
        ingredientData = ingredientData?.toIngredients(),
        nutrientPercentages = nutrientPercentages?.toNutrientPercentage(),
        nutritionDataPerGm = nutritionDataPerGm,
        isSafeForUser = isSafeForUser
    )
}

@Serializable
data class IngredientDataDto(
    val ingredients: List<String>?,
    val allergens: List<String>?
) {
    fun toIngredients() = IngredientData(
        ingredients = ingredients,
        allergens = allergens
    )
}

@Serializable
data class NutrientPercentagesDto(
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
) {
    fun toNutrientPercentage() = NutrientPercentages(
        carbohydrates_100g,
        energy_kcal_100g,
        energy_100g,
        fat_100g,
        fiber_100g,
        fruits_vegetables_legumes_estimate_from_ingredients_100g,
        fruits_vegetables_nuts_estimate_from_ingredients_100g,
        nova_group_100g,
        proteins_100g,
        salt_100g,
        saturated_fat_100g,
        sodium_100g,
        sugars_100g
    )
}

//Recommend
@Serializable
data class NutrientRequestDto(
    val nutrientInput: NutrientInputDto,
    val ingredients: List<String>,
)

@Serializable
data class NutrientInputDto(
    val calories: Float,
    val fatContent: Float,
    val saturatedFatContent: Float,
    val cholesterolContent: Float,
    val carbohydrateContent: Float,
    val fiberContent: Float,
    val sugarContent: Float,
    val proteinContent: Float
)

@Serializable
data class RecipeResponseDto(
    val data: RecipeDataDto?,
    val message: String?
)

@Serializable
data class RecipeDataDto(
    val output: List<RecipeDto>?
) {
    fun toRecipeData() = RecipeData(
        output = output?.map { it.toRecipe() }
    )
}

@Serializable
data class RecipeDto(
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
) {
    fun toRecipe() = Recipe(
        Name,
        CookTime,
        PrepTime,
        TotalTime,
        RecipeIngredientParts,
        Calories,
        FatContent,
        SaturatedFatContent,
        CholesterolContent,
        SodiumContent,
        CarbohydrateContent,
        FiberContent,
        SugarContent,
        ProteinContent,
        RecipeInstructions
    )
}

//Image
@Serializable
data class ImageAllergenDetectionResponseDto(
    val data: DetectionDataDto?,
    val message: String?
)

@Serializable
data class DetectionDataDto(
    val inference_id: String?,
    val time: Double?,
    val image: ImageDetailsDto?,
    val predictions: List<PredictionDto>?,
    val isSafeForUser: Boolean
) {
    fun toDetection() = DetectionData(
        inference_id,time,image = image!!.toImageDetail() ,predictions = predictions?.map { it.toPrediction() },isSafeForUser
    )
}

@Serializable
data class ImageDetailsDto(
    val width: Int,
    val height: Int
) {
    fun toImageDetail() =
        ImageDetails(
            width, height
        )

}

@Serializable
data class PredictionDto(
    val x: Float?,
    val y: Float?,
    val width: Float?,
    val height: Float?,
    val confidence: Float?,
    val `class`: String?,
    val class_id: Int?,
    val detection_id: String?
) {
    fun toPrediction() = Prediction(
        x, y, width, height, confidence, `class`, class_id, detection_id
    )
}

//Label
@Serializable
data class LabelDto(
    val ingredients: String,
    val userId: String

)

data class LabelResponseDto(
    val data: LabelDataDto,
    val message: String
)

data class LabelDataDto(
    val allergen: List<String>?, // Assuming allergens are represented as a list of strings
    val isSafeForUser: Boolean
) {
    fun toLabelData() = LabelData(
        allergen,isSafeForUser
    )
}
