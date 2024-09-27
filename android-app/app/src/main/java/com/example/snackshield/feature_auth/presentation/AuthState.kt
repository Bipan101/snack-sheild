package com.example.snackshield.feature_auth.presentation

sealed interface AuthUiState {
    data object Remaining : AuthUiState
    data object Loading : AuthUiState
    data object Success : AuthUiState
    data class Error(val error: String) : AuthUiState
}