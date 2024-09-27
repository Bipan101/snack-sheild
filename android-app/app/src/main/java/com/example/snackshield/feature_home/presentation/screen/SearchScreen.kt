package com.example.snackshield.feature_home.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.snackshield.common.components.Spacing

@Composable
fun SearchScreen(goBack: () -> Unit) {
    var search by rememberSaveable {
        mutableStateOf("")
    }
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
    Column {
        SearchTopBar(
            search, onSearchChange = { search = it }, focusRequester, goBack
        )

    }
}

@Composable
fun SearchTopBar(
    search: String,
    onSearchChange: (String) -> Unit,
    focusRequester: FocusRequester,
    goBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            goBack()
        }) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew, contentDescription = "back",
                modifier = Modifier.size(24.dp)
            )
        }
        SearchTextField(
            value = search,
            onValueChange = onSearchChange,
            placeholder = {
                Text("Search...")
            },
            focusRequester = focusRequester
        )
    }
    Spacing(height = 4)
    HorizontalDivider()
}

@Composable
fun SearchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly: Boolean = false,
    focusRequester: FocusRequester
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = placeholder,
        label = label,
        readOnly = readOnly,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, fontWeight = FontWeight.W400),
        visualTransformation = visualTransformation, shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
        ),
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxWidth(),
    )
}