package com.example.snackshield.feature_auth.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snackshield.common.components.AppTextField
import com.example.snackshield.common.components.ConfirmDialog
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.feature_auth.domain.model.Detail
import com.example.snackshield.feature_auth.presentation.AuthEvents
import com.example.snackshield.feature_auth.presentation.AuthUiState
import com.example.snackshield.feature_auth.presentation.AuthViewModel
import com.example.snackshield.feature_auth.presentation.components.SubmitButton
import com.example.snackshield.feature_scan.presentation.ScanUiState

const val TAG = "DETAIL_SCREEN"

@Composable
fun DetailScreen(goBack : ()-> Unit,toHome: () -> Unit, authViewModel: AuthViewModel) {
    var age by remember {
        mutableStateOf("")
    }
    var weight by remember {
        mutableStateOf("")
    }
    var height by remember { mutableStateOf("") }
    var gender by remember {
        mutableStateOf("")
    }
    var allergies by remember {
        mutableStateOf(emptyList<String>())
    }
    var dietPreference by remember {
        mutableStateOf("")
    }
    var showConfirmDialog by remember { mutableStateOf(false) }
    BackHandler(enabled = true) {
        showConfirmDialog = true
    }
    val event = authViewModel::onEvent
    val uiState by authViewModel.authState.collectAsState()
    val state by authViewModel.state.collectAsState()
    LaunchedEffect(true) { 
        event.invoke(AuthEvents.GetUserId)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        item {
            DetailView(
                age, weight, height, gender, allergies,dietPreference,
                onAgeChange = { age = it },
                onWeightChange = { weight = it },
                onHeightChange = {
                    height = it
                }, onGenderChange = {
                    gender = it
                },
                onAllergiesChange = {
                    allergies = if (allergies.contains(it)) {
                        allergies - it // Create a new list without the allergy
                    } else {
                        allergies + it // Create a new list with the allergy added
                    }
                    Log.d(TAG, "DetailScreen: $allergies")
                },
                onDietChange = {
                    dietPreference = it
                },
                onSubmit = {
                    event.invoke(AuthEvents.UserDetail(Detail(age.toInt(),weight.toInt(),height.toInt(),gender,allergies, state.userId!! ,dietPreference )))
                },
                uiState,
                toHome
            )
        }
    }
    if (showConfirmDialog) {
        ConfirmDialog(
            onDismissRequest = { showConfirmDialog = false },
            goBack = goBack
        )
    }
}

@Composable
fun DetailTopBar() {
    Text(
        text = "Add your details to",
        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.SemiBold)
    )
    Text(
        "SnackShield!",
        style = MaterialTheme.typography.displayMedium.copy(
            color = MaterialTheme.colorScheme.outlineVariant,
            fontWeight = FontWeight.SemiBold
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    age: String,
    weight: String,
    height: String,
    gender: String,
    allergies: List<String>,
    dietPreference : String,
    onAgeChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onAllergiesChange: (String) -> Unit,
    onDietChange : (String) -> Unit,
    onSubmit : () -> Unit,
    uiState: AuthUiState,
    toHome: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (showBottomSheet) {

            BottomSheet(
                sheetState,
                onDismiss = { showBottomSheet = false },
                onAllergiesChange,
                allergies
            )
        }

        Spacing(height = 40)
        DetailTopBar()
        Spacing(height = 40)
        DetailTextFields(
            age,
            weight,
            height,
            gender,
            allergies,
            dietPreference,
            onAgeChange,
            onWeightChange,
            onHeightChange,
            onGenderChange,
            onDietChange,
            onShowBottomSheet = {
                showBottomSheet = true
            }
        )
        Spacing(height = 24)
        when(uiState) {
            is AuthUiState.Error ->  SubmitButton(text = "Error") {
                onSubmit()
            }
            AuthUiState.Loading -> SubmitButton(text = "Loading...") {
            }
            AuthUiState.Remaining -> SubmitButton(text = "Done") {
                onSubmit()
            }
            AuthUiState.Success -> {
                SubmitButton(text = "Done") {
                }
                toHome()
            }

        }

    }
}

@Composable
fun DetailTextFields(
    age: String,
    weight: String,
    height: String,
    gender: String,
    allergies: List<String>,
    dietPreference: String,
    onAgeChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onDietChange: (String) -> Unit,
    onShowBottomSheet: () -> Unit
) {
    DetailTextField(
        identifier = "Age",
        placeholder = "Enter age",
        icon = Icons.Default.Numbers,
        age,
        onAgeChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacing(height = 12)
    DetailTextField(
        identifier = "Weight",
        placeholder = "Enter weight(in KG)",
        icon = Icons.Default.LineWeight,
        weight,
        onWeightChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacing(height = 12)
    DetailTextField(
        identifier = "Height",
        placeholder = "Enter height(in cm)",
        icon = Icons.Default.Numbers,
        height,
        onHeightChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacing(height = 12)
    DetailTextField(
        identifier = "Gender",
        placeholder = "Enter Gender",
        icon = Icons.Default.Numbers,
        gender,
        onGenderChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
    Spacing(height = 12)
    DropDownInfo(dietPreference,onDietChange)
    Spacing(height = 12)
    AllergiesView(onShowBottomSheet, allergies)
}

@Composable
fun DropDownInfo(dietPreference: String, onDietChange: (String) -> Unit) {

        AppTextField(
            identifier = "Reason for joining",
            value = dietPreference,
            onValueChange = onDietChange,
            placeholder = {
                Text("Why do you want to join?")
            },
            modifier = Modifier
                .fillMaxWidth()
        )
    }

@SuppressLint("SuspiciousIndentation")
@Composable
fun AllergiesView(onShowBottomSheet: () -> Unit, allergies: List<String>) {
    val state = rememberLazyListState()
    Text(
        text = "Choose allergies", style = MaterialTheme.typography.headlineSmall.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
    )
    Spacing(height = 8)
    if (allergies.isEmpty()) {
        TextButton(
            onClick = { onShowBottomSheet() },
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.inverseSurface
            ),
            contentPadding = PaddingValues(4.dp),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "add")
        }
    } else {
        LazyRow(
            state = state,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .clickable { onShowBottomSheet() }
                .padding(16.dp)
        ) {
            itemsIndexed(allergies) { index, allergy ->
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
                    Text(allergy)
                }
                Spacing(width = 12)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onAllergiesChange: (String) -> Unit,
    allergies: List<String>
) {
    val lazyGridState = rememberLazyGridState()
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            state = lazyGridState
        ) {
            allergiesData.forEachIndexed { _, allergyData ->
                item(span = { GridItemSpan(3) }) {
                    Text(
                        text = allergyData.category,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)

                    )
                }
                items(allergyData.allergies) { allergy ->
                    val colors = if (allergies.contains(allergy)) {
                        MaterialTheme.colorScheme.surfaceContainer
                    } else {
                        MaterialTheme.colorScheme.outlineVariant
                    }
                    TextButton(
                        onClick = { onAllergiesChange(allergy) },
                        colors = ButtonDefaults.textButtonColors(
                            containerColor = colors,
                            contentColor = MaterialTheme.colorScheme.inverseSurface
                        ),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .padding(4.dp)
                    ) {
                        Text(
                            text = allergy,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailTextField(
    identifier: String,
    placeholder: String,
    icon: ImageVector,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions
) {
    AppTextField(
        identifier = identifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(placeholder)
        },
        leadingIcon = {
            Icon(
                imageVector = icon, contentDescription = identifier, modifier = Modifier
                    .size(20.dp)
            )
        },
        keyboardOptions = keyboardOptions
        )
}


data class AllergyData(
    val category: String,
    val allergies: List<String>
)

val allergiesData = listOf(
    AllergyData(
        category = "Nuts & Seeds",
        allergies = listOf(
            "Peanuts", "Tree Nuts", "Sunflower Seeds", "Poppy Seeds", "Pumpkin Seeds",
            "Coconut", "Sesame", "Almonds", "Cashews", "Walnuts",
            "Pecans", "Hazelnuts", "Macadamia Nuts", "Brazil Nuts"
        )
    ),
    AllergyData(
        category = "Seafood & Fish",
        allergies = listOf(
            "Shellfish", "Fish", "Shrimp", "Crab", "Lobster",
            "Clams", "Oysters", "Scallops", "Mussels", "Squid",
            "Tuna", "Salmon", "Cod", "Halibut", "Sardines"
        )
    ),
    AllergyData(
        category = "Dairy & Eggs",
        allergies = listOf(
            "Milk", "Eggs", "Cheese", "Yogurt", "Butter",
            "Cream", "Ghee", "Ice Cream", "Goat's Milk", "Sheep's Milk"
        )
    ),
    AllergyData(
        category = "Grains & Gluten",
        allergies = listOf(
            "Wheat", "Gluten", "Barley", "Oats", "Rye",
            "Spelt", "Quinoa", "Buckwheat", "Rice", "Corn",
            "Millet", "Teff", "Sorghum", "Farro", "Amaranth"
        )
    ),
    AllergyData(
        category = "Fruits",
        allergies = listOf(
            "Kiwi", "Banana", "Avocado", "Strawberries", "Pineapple",
            "Mango", "Papaya", "Tomato", "Blueberries", "Raspberries",
            "Blackberries", "Grapes", "Cherries", "Melon", "Peach",
            "Plum", "Apple", "Pear", "Orange", "Lemon", "Lime"
        )
    ),
    AllergyData(
        category = "Vegetables & Legumes",
        allergies = listOf(
            "Potato", "Garlic", "Onion", "Carrots", "Peas",
            "Chickpeas", "Lentils", "Beans", "Broccoli", "Cauliflower",
            "Spinach", "Kale", "Cabbage", "Celery", "Zucchini",
            "Cucumber", "Bell Pepper", "Asparagus", "Eggplant", "Mushrooms"
        )
    ),
    AllergyData(
        category = "Meats & Poultry",
        allergies = listOf(
            "Beef", "Chicken", "Pork", "Turkey", "Lamb",
            "Duck", "Goose", "Venison", "Rabbit", "Bison",
            "Goat", "Sausage", "Bacon", "Ham", "Hot Dogs"
        )
    ),
    AllergyData(
        category = "Spices & Additives",
        allergies = listOf(
            "Cinnamon", "Mustard", "Sulphites", "Garlic", "Onion Powder",
            "Paprika", "Chili Powder", "Black Pepper", "Cumin", "Turmeric",
            "Ginger", "Nutmeg", "Cloves", "Cocoa", "Chocolate",
            "Vanilla", "Soy Sauce", "MSG", "Baking Powder", "Baking Soda"
        )
    ),
    AllergyData(
        category = "Beverages & Alcohol",
        allergies = listOf(
            "Wine", "Beer", "Cider", "Whiskey", "Vodka",
            "Gin", "Rum", "Tequila", "Champagne", "Soda",
            "Coffee", "Tea", "Energy Drinks", "Milkshakes", "Hot Chocolate"
        )
    )
)