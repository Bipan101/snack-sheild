package com.example.snackshield.feature_auth.presentation.screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LineWeight
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.snackshield.common.components.AppTextField
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.feature_auth.presentation.AuthUiState
import com.example.snackshield.feature_auth.presentation.AuthViewModel
import com.example.snackshield.feature_auth.presentation.components.SubmitButton

const val TAG = "DETAIL_SCREEN"

@Composable
fun DetailScreen(toHome : () -> Unit , authViewModel: AuthViewModel) {
    var age by remember {
        mutableStateOf("")
    }
    var weight by remember {
        mutableStateOf("")
    }
    var height by remember { mutableStateOf("") }
    var gender by remember {
        mutableStateOf("male")
    }
    var allergies by remember {
        mutableStateOf(emptyList<String>())
    }
    var allergy by remember {
        mutableStateOf("")
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        item {
            DetailView(
                age, weight, height, gender, allergy,
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
                },
                toHome
            )
        }
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

@Composable
fun DetailTextFields(
    age: String,
    weight: String,
    height: String,
    gender: String,
    allergy: String,
    onAgeChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onAllergiesChange: (String) -> Unit,
) {
    DetailTextField(identifier = "Age", placeholder = "Enter age" , icon = Icons.Default.Numbers,age,onAgeChange)
    Spacing(height = 12)
    DetailTextField(identifier = "Weight", placeholder = "Enter weight" , icon = Icons.Default.LineWeight,weight,onWeightChange)
    Spacing(height = 12)
    DetailTextField(identifier = "Height", placeholder = "Enter height" , icon = Icons.Default.Numbers,height,onHeightChange)
    Spacing(height = 12)
    DetailTextField(identifier = "Gender", placeholder = "Enter Gender" , icon = Icons.Default.Numbers,gender,onGenderChange)
    Spacing(height = 12)
    AllergyTextField(allergy,onAllergiesChange)
    Spacing(height = 12)
}

@Composable
fun DetailTextField(identifier : String,placeholder: String,icon : ImageVector, value : String,onValueChange : (String) -> Unit) {
    AppTextField(
        identifier = identifier,
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(placeholder)
        },
        leadingIcon = {
            Icon(imageVector = icon, contentDescription = identifier , modifier = Modifier
                .size(20.dp)
            )
        }
    )
}

@Composable
fun AllergyTextField(value : String, onValueChange: (String) -> Unit) {
    AppTextField(
        identifier = "Allergies",
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text("Enter allergies")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.AllInbox, contentDescription = "allergy" , modifier = Modifier
                .size(20.dp)
            )
        }
    )
}
@Composable
fun DetailView(
    age: String,
    weight: String,
    height: String,
    gender: String,
    allergies: String,
    onAgeChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit,
    onGenderChange: (String) -> Unit,
    onAllergiesChange: (String) -> Unit,
    toHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacing(height = 40)
        DetailTopBar()
        Spacing(height = 40)
        DetailTextFields(
            age,
            weight,
            height,
            gender,
            allergies,
            onAgeChange,
            onWeightChange,
            onHeightChange,
            onGenderChange,
            onAllergiesChange
        )
        Spacing(height = 24)
        SubmitButton(text = "Done") {
            toHome()
        }
    }
}
