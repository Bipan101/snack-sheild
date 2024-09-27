package com.example.snackshield.feature_auth.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.snackshield.common.components.AppTextField


@Composable
fun PasswordTextField(value : String , onValueChange : (String) -> Unit) {
    var passwordSeen by remember {
        mutableStateOf(false)
    }
    AppTextField(
        identifier = "Password",
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text("Enter password...")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = "password" , modifier = Modifier
                .size(20.dp)
            )
        },
        trailingIcon = {
            Icon(imageVector = if (passwordSeen) Icons.Default.VisibilityOff  else Icons.Default.Visibility, contentDescription = "password" , modifier = Modifier
                .size(20.dp)
                .clickable {
                    passwordSeen = !passwordSeen
                }
            )
        },
        visualTransformation = if (passwordSeen) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun EmailTextField(value : String , onValueChange : (String) -> Unit) {
    AppTextField(
        identifier = "Email",
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text("Enter email...")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Email, contentDescription = "inbox" , modifier = Modifier
                .size(20.dp)
            )
        }
    )
}

@Composable
fun NameTextField(value : String , onValueChange : (String) -> Unit) {
    AppTextField(
        identifier = "Full name",
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text("Enter full name...")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = "name" , modifier = Modifier
                .size(20.dp)
            )
        }
    )
}