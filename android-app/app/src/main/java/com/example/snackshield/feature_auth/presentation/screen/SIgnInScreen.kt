package com.example.snackshield.feature_auth.presentation.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.feature_auth.domain.model.SignIn
import com.example.snackshield.feature_auth.presentation.AuthEvents
import com.example.snackshield.feature_auth.presentation.AuthUiState
import com.example.snackshield.feature_auth.presentation.AuthViewModel
import com.example.snackshield.feature_auth.presentation.components.EmailTextField
import com.example.snackshield.feature_auth.presentation.components.PasswordTextField
import com.example.snackshield.feature_auth.presentation.components.SubmitButton

@Composable
fun SignInScreen(toSignUp: () -> Unit, toHome: () -> Unit, authViewModel: AuthViewModel) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    val event = authViewModel::onEvent
    val state by authViewModel.authState.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        item {
            SignInView(
                email = email,
                password = password,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onSubmit = {
                    event.invoke(AuthEvents.UserSignIn(SignIn(email, password)))
                },
                toSignUp,
                toHome,
                state
            )
        }
    }

}

@Composable
private fun SignInView(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    toSignUp: () -> Unit,
    toHome: () -> Unit,
    state: AuthUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacing(height = 40)
        SignInTopBar()
        Spacing(height = 40)
        SignInTextFields(email, onEmailChange, password, onPasswordChange)
        Spacing(height = 24)
        when (state) {
            is AuthUiState.Error -> {
                SubmitButton(text = state.error) {
                    onSubmit()
                }
                toHome()

            }

            AuthUiState.Loading -> {
                SubmitButton(text = "Loading....") {

                }
                toHome()

            }

            AuthUiState.Remaining -> {
                SubmitButton(text = "Sign In") {
                    onSubmit()
                }
//                toHome()

            }

            AuthUiState.Success -> {
                SubmitButton(text = "Sign In") {

                }
                toHome()
            }
        }
        Spacing(height = 24)
        SignInBottomBar(toSignUp = toSignUp)
    }
}

@Composable
private fun SignInTopBar() {
    Text(
        text = "Welcome back to",
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
fun SignInTextFields(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    EmailTextField(value = email, onValueChange = onEmailChange)
    Spacing(height = 12)
    PasswordTextField(value = password, onValueChange = onPasswordChange)
}

@Composable
private fun SignInBottomBar(toSignUp: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            "Don't have an account ? ",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            "Sign Up",
            modifier = Modifier.clickable { toSignUp() },
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        )
    }
}
