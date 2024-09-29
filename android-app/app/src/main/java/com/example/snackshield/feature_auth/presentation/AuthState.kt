package com.example.snackshield.feature_auth.presentation

import com.example.snackshield.feature_scan.domain.model.DataFromBarcode

sealed interface AuthUiState {
    data object Remaining : AuthUiState
    data object Loading : AuthUiState
    data object Success : AuthUiState
    data class Error(val error: String) : AuthUiState
}

data class AuthState(
    val userId : String? = null
)