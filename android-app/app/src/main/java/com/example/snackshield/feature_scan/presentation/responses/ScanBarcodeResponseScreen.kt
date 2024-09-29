package com.example.snackshield.feature_scan.presentation.responses

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.snackshield.common.components.AppTopBar
import com.example.snackshield.common.components.EmptyScreen
import com.example.snackshield.common.components.FoodStatus
import com.example.snackshield.common.components.SliderComp
import com.example.snackshield.feature_scan.domain.model.DataFromBarcode
import com.example.snackshield.feature_scan.presentation.ScanViewModel

@Composable
fun ScanBarcodeResponseScreen(scanViewModel: ScanViewModel, goBack: () -> Unit) {
    BackHandler {
        goBack()
    }
    val state by scanViewModel.state.collectAsState()
    if (state.productFormBarcode != null) {
        ScanBarcodeResponseUi(
            state.productFormBarcode,
            goBack = { goBack() },
        )
    } else {
        EmptyScreen()
    }

}

@Composable
fun ScanBarcodeResponseUi(
    productData: DataFromBarcode?,
    goBack: () -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        productData?.let {
            item {
                productData.productName?.let { it ->
                    AppTopBar(identifier = it) {
                        goBack()
                    }
                }
                SliderComp(
                    identifier = "Fat",
                    value = productData.nutrientPercentages!!.fat_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 100f // Fat can be a maximum of 100g per 100g of food
                )
                SliderComp(
                    identifier = "Energy calorie",
                    value = productData.nutrientPercentages.energy_kcal_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 900f // Max 900 kcal for 100g (if it's all fat)
                )
                SliderComp(
                    identifier = "Carbohydrate",
                    value = productData.nutrientPercentages.carbohydrates_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 100f // Max 100g per 100g of food
                )
                SliderComp(
                    identifier = "Saturated Fat",
                    value = productData.nutrientPercentages.saturated_fat_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 100f // Saturated fat can also max out at 100g
                )
                SliderComp(
                    identifier = "Energy",
                    value = productData.nutrientPercentages.energy_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 3760f // Max 3760 kJ for 100g (if it's all fat)
                )
                SliderComp(
                    identifier = "Fiber",
                    value = productData.nutrientPercentages.fiber_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 50f // Fiber typically maxes out around 50g in high-fiber foods
                )
                SliderComp(
                    identifier = "Fruits and vegetable legumes estimate",
                    value = productData.nutrientPercentages.fruits_vegetables_legumes_estimate_from_ingredients_100g?.toFloat()
                        ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 100f // Max is 100% if the food is entirely from plant sources
                )
                SliderComp(
                    identifier = "Fruits and vegetable nuts estimate",
                    value = productData.nutrientPercentages.fruits_vegetables_nuts_estimate_from_ingredients_100g?.toFloat()
                        ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 100f // Max is 100% for foods primarily made from these ingredients
                )
                SliderComp(
                    identifier = "Nova group",
                    value = productData.nutrientPercentages.nova_group_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 4f // NOVA group ranges from 1 to 4 (classification of food processing)
                )
                SliderComp(
                    identifier = "Proteins",
                    value = productData.nutrientPercentages.proteins_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 100f // Maximum protein content can be 100g in 100g of food
                )
                SliderComp(
                    identifier = "Salt",
                    value = productData.nutrientPercentages.salt_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 100f // Salt can theoretically be 100g in 100g of pure salt
                )
                SliderComp(
                    identifier = "Sodium",
                    value = productData.nutrientPercentages.sodium_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 39.3f // Sodium content in 100g of salt can be up to 39.3g
                )
                SliderComp(
                    identifier = "Sugar content",
                    value = productData.nutrientPercentages.sugars_100g?.toFloat() ?: 0f,
                    onValueChange = {},
                    start = 0f,
                    end = 100f // Sugars are a type of carbohydrate, so max is 100g
                )
                FoodStatus(productData.isSafeForUser)
            }
        }
    }
}





