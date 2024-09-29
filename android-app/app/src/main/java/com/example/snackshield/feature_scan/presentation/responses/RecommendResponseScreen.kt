package com.example.snackshield.feature_scan.presentation.responses

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.snackshield.common.components.AppTopBar
import com.example.snackshield.common.components.EmptyScreen
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.feature_scan.domain.model.Recipe
import com.example.snackshield.feature_scan.presentation.ScanViewModel


@Composable
fun RecommendResponseScreen(scanViewModel: ScanViewModel, goBack: () -> Unit) {
    BackHandler {
        goBack()
    }
    val state by scanViewModel.state.collectAsState()
    Log.d("Hello", "RecommendResponseScreen: ${state.recommendedProduct} ")
    if (state.recommendedProduct != null && state.recommendedProduct!!.output != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            ) {
                AppTopBar(identifier = "Recommend response") {
                    goBack()
                }
                RecommendResponseView(
                    state.recommendedProduct!!.output!!
                )
            }

    } else {
        EmptyScreen()
    }
}

@Composable
fun RecommendResponseView(recipeData: List<Recipe>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(recipeData) { recipe ->
            RecipeItemView(recipe)
            Spacing(height = 12)
        }
    }
}

@Composable
fun RecipeItemView(recipe: Recipe) {

    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { isExpanded = !isExpanded }, // Toggle expand state on click
        elevation = CardDefaults.cardElevation(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Recipe Title
            recipe.Name?.let { Text(text = it, style = MaterialTheme.typography.headlineSmall) }

            // Show some basic info
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Cook Time: ${recipe.CookTime} min | Prep Time: ${recipe.PrepTime} min")

            // Show more details if expanded
            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))

                // Ingredients
                Text(text = "Ingredients:", style = MaterialTheme.typography.labelLarge)
                recipe.RecipeIngredientParts?.forEach { ingredient ->
                    Text(text = "- $ingredient")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Nutritional Info
                Text(text = "Nutritional Info:", style = MaterialTheme.typography.labelMedium)
                Text(text = "Calories: ${recipe.Calories} kcal")
                Text(text = "Fat: ${recipe.FatContent}g, Saturated Fat: ${recipe.SaturatedFatContent}g")
                Text(text = "Cholesterol: ${recipe.CholesterolContent}mg")
                Text(text = "Sodium: ${recipe.SodiumContent}mg")
                Text(text = "Carbs: ${recipe.CarbohydrateContent}g, Fiber: ${recipe.FiberContent}g, Sugar: ${recipe.SugarContent}g")
                Text(text = "Protein: ${recipe.ProteinContent}g")

                Spacer(modifier = Modifier.height(8.dp))

                // Instructions
                Text(text = "Instructions:", style = MaterialTheme.typography.labelMedium)
                recipe.RecipeInstructions?.forEachIndexed { index, instruction ->
                    Text(text = "${index + 1}. $instruction")
                }
            }
        }
    }
}

