package com.example.snackshield.feature_auth.presentation.screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.snackshield.common.components.ConfirmDialog
import com.example.snackshield.common.components.Spacing
import com.example.snackshield.feature_auth.domain.model.SignUp
import com.example.snackshield.feature_auth.presentation.AuthEvents
import com.example.snackshield.feature_auth.presentation.AuthUiState
import com.example.snackshield.feature_auth.presentation.AuthViewModel
import com.example.snackshield.feature_auth.presentation.components.EmailTextField
import com.example.snackshield.feature_auth.presentation.components.NameTextField
import com.example.snackshield.feature_auth.presentation.components.PasswordTextField
import com.example.snackshield.feature_auth.presentation.components.SubmitButton

@Composable
fun SignUpScreen(goBack: () -> Unit, toDetail: () -> Unit, authViewModel: AuthViewModel) {
    var email by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var name by rememberSaveable {
        mutableStateOf("")
    }
    val event = authViewModel::onEvent
    val state by authViewModel.authState.collectAsState()
    var showConfirmDialog by remember { mutableStateOf(false) }
    BackHandler(enabled = true) {
        showConfirmDialog = true
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
    ) {
        item {
            SignUpView(
                name = name,
                onNameChange = { name = it },
                email = email,
                password = password,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onSubmit = {
                    event.invoke(AuthEvents.UserSignUp(SignUp(name, email, password)))
                },
                goBack,
                toDetail,
                state
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
private fun SignUpView(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSubmit: () -> Unit,
    goBack: () -> Unit,
    toDetail: () -> Unit,
    state: AuthUiState
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacing(height = 40)
        SignUpTopBar()
        Spacing(height = 40)
        SignUpTextFields(name, onNameChange, email, onEmailChange, password, onPasswordChange)
        Spacing(height = 24)
        when (state) {
            is AuthUiState.Error -> {
                SubmitButton(text = state.error) {
                    onSubmit()
                }

            }

            AuthUiState.Loading -> {
                SubmitButton(text = "Loading....") {

                }

            }

            AuthUiState.Remaining -> {
                SubmitButton(text = "Sign Up") {
                    onSubmit()
                }
            }

            AuthUiState.Success -> {
                SubmitButton(text = "Sign Up") {

                }
                toDetail()
            }
        }

        Spacing(height = 24)
        SignUpBottomBar(goBack = goBack)
    }
}

@Composable
private fun SignUpTopBar() {
    Text(
        text = "Let's make your",
        style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.SemiBold)
    )
    Text(
        "account!",
        style = MaterialTheme.typography.displayMedium.copy(
            color = MaterialTheme.colorScheme.outlineVariant,
            fontWeight = FontWeight.SemiBold
        )
    )

}

@Composable
fun SignUpTextFields(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    NameTextField(value = name, onValueChange = onNameChange)
    Spacing(height = 12)
    EmailTextField(value = email, onValueChange = onEmailChange)
    Spacing(height = 12)
    PasswordTextField(value = password, onValueChange = onPasswordChange)
}

@Composable
private fun SignUpBottomBar(goBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            "Already have an account ? ",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
        )
        Text(
            "Sign In",
            modifier = Modifier.clickable { goBack() },
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        )
    }
}