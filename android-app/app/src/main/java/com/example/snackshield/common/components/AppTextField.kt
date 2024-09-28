package com.example.snackshield.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    identifier : String = "",
    value:String,
    onValueChange : (String) -> Unit,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    readOnly : Boolean = false
) {
    Column {
        if (identifier.isNotEmpty()) {
            Text(text = identifier , style = MaterialTheme.typography.headlineSmall.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            ))
            Spacing(height = 8)
        }
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            label = label ,
            readOnly = readOnly,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            textStyle = LocalTextStyle.current.copy(fontSize = 20.sp, fontWeight = FontWeight.W400),
            visualTransformation = visualTransformation, shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
            ),
            modifier = modifier
                .fillMaxWidth(),
        )
    }
}

