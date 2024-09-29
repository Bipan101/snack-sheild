package com.example.snackshield.feature_scan.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snackshield.common.components.AppTextField
import com.example.snackshield.common.components.AppTopBar
import com.example.snackshield.common.components.SliderComp
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.feature_auth.presentation.components.SubmitButton
import com.example.snackshield.feature_scan.domain.model.NutrientInput
import com.example.snackshield.feature_scan.domain.model.NutrientRequest
import com.example.snackshield.feature_scan.presentation.ScanEvent
import com.example.snackshield.feature_scan.presentation.ScanUiState
import com.example.snackshield.feature_scan.presentation.ScanViewModel

@Composable
fun RecommendScreen(scanViewModel: ScanViewModel, goBack: () -> Unit, toResponse : () -> Unit) {
    var calories by remember { mutableFloatStateOf(0f) }

    var fatContent by remember { mutableFloatStateOf(0f) }

    var saturatedFatContent by remember { mutableFloatStateOf(0f) }

    var cholesterolContent by remember { mutableFloatStateOf(0f) }

    var carbohydrateContent by remember { mutableFloatStateOf(0f) }

    var fiberContent by remember { mutableFloatStateOf(0f) }

    var sugarContent by remember { mutableFloatStateOf(0f) }

    var proteinContent by remember { mutableFloatStateOf(0f) }

    var ingredients by remember {
        mutableStateOf(emptyList<String>())
    }
    val event = scanViewModel::onEvent
    val scanState by scanViewModel.scanState.collectAsState()
    Column {
        AppTopBar(identifier = "Recommendation") {
            goBack()
            scanViewModel.resetState()
        }
        RecommendView(
            calories = calories,
            fatContent = fatContent,
            saturatedFatContent = saturatedFatContent,
            cholesterolContent = cholesterolContent,
            carbohydrateContent = carbohydrateContent,
            fiberContent = fiberContent,
            sugarContent = sugarContent,
            proteinContent = proteinContent,
            ingredients,
            onCalorieChange = { calories = it },
            onFatContentChange = { fatContent = it },
            onSaturatedFatContentChange = { saturatedFatContent = it },
            onCholesterolContentChange = { cholesterolContent = it },
            onCarbohydrateContentChange = { carbohydrateContent = it },
            onFiberContentChange = { fiberContent = it },
            onSugarContentChange = { sugarContent = it },
            onProteinContentChange = { proteinContent = it },
            onIngredientsChange = {
                ingredients = if (ingredients.contains(it)) {
                    ingredients - it // Create a new list without the allergy
                } else {
                    ingredients + it // Create a new list with the allergy added
                }
            },
            onSubmit = {
                event.invoke(
                    ScanEvent.Recommend(
                        NutrientRequest(
                            NutrientInput(
                                calories,
                                fatContent,
                                saturatedFatContent,
                                cholesterolContent,
                                carbohydrateContent,
                                fiberContent,
                                sugarContent,
                                proteinContent
                            ), ingredients
                        )
                    )
                )
            },
            scanState = scanState,
            toResponse
        )

    }
}

data class Quintuple<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)

@Composable
fun RecommendView(
    calories: Float,
    fatContent: Float,
    saturatedFatContent: Float,
    cholesterolContent: Float,
    carbohydrateContent: Float,
    fiberContent: Float,
    sugarContent: Float,
    proteinContent: Float,
    ingredients: List<String>,
    onCalorieChange: (Float) -> Unit,
    onFatContentChange: (Float) -> Unit,
    onSaturatedFatContentChange: (Float) -> Unit,
    onCholesterolContentChange: (Float) -> Unit,
    onCarbohydrateContentChange: (Float) -> Unit,
    onFiberContentChange: (Float) -> Unit,
    onSugarContentChange: (Float) -> Unit,
    onProteinContentChange: (Float) -> Unit,
    onIngredientsChange: (String) -> Unit,
    onSubmit: () -> Unit,
    scanState: ScanUiState,
    toResponse: () -> Unit
) {
    // Define the fields using Quintuple with min, max, current, and change handler
    val fields = listOf(
        Quintuple("Calories", 0f, 2000f, calories, onCalorieChange),
        Quintuple("Fat Content", 0f, 100f, fatContent, onFatContentChange),
        Quintuple("Saturated Fat", 0f, 13f, saturatedFatContent, onSaturatedFatContentChange),
        Quintuple("Cholesterol", 0f, 300f, cholesterolContent, onCholesterolContentChange),
        Quintuple("Carbohydrates", 0f, 2300f, carbohydrateContent, onCarbohydrateContentChange),
        Quintuple("Fiber", 0f, 325f, fiberContent, onFiberContentChange),
        Quintuple("Sugar", 0f, 50f, sugarContent, onSugarContentChange),
        Quintuple("Protein", 0f, 48f, proteinContent, onProteinContentChange)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
            .imePadding()
    ) {
        items(fields) { field ->
            SliderComp(
                identifier = field.first,
                value = field.fourth,
                onValueChange = field.fifth,
                start = field.second,
                end = field.third
            )
        }
        item {
            AddIngredients(onIngredientsChange, ingredients)
            Spacing(height = 16)
        }
        item {
            when (scanState) {
                is ScanUiState.Error -> SubmitButton(text = scanState.error) {
                    onSubmit()
                }

                ScanUiState.Loading -> SubmitButton(text = "Loading...") {
                    onSubmit()
                }

                ScanUiState.Remaining -> SubmitButton(text = "Done") {
                    onSubmit()
                }

                ScanUiState.Success -> {
                    SubmitButton(text = "Done") {

                    }
                    toResponse()
                }
            }
        }
    }
}


@Composable
fun AddIngredients(onAddClick: (String) -> Unit, ingredients: List<String>) {
    val state = rememberLazyListState()
    var ingredient by remember {
        mutableStateOf("")
    }
    AppTextField(
        identifier = "Add ingredient",
        value = ingredient,
        onValueChange = { ingredient = it },
        placeholder = {
            Text("Add ingredients to list")
        },
        trailingIcon = {
            if (ingredient.isNotEmpty()) {
                IconButton(onClick = {
                    onAddClick(ingredient)
                    ingredient = ""
                }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
    )
    Spacing(height = 16)
    if (ingredients.isNotEmpty()) {
        LazyRow(
            state = state,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .padding(16.dp)
        ) {
            itemsIndexed(ingredients) { index, ingredient ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Text(
                            text = "${(index + 1)}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                    Spacing(width = 4)
                    Text(ingredient)
                }
                Spacing(width = 12)
            }
        }
    }
}