package com.example.snackshield.feature_auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.snackshield.common.domain.repo.SessionManager
import com.example.snackshield.feature_auth.domain.model.SignIn
import com.example.snackshield.feature_auth.domain.model.SignUp
import com.example.snackshield.feature_auth.domain.model.User
import com.example.snackshield.feature_auth.domain.repo.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val authRepo: AuthRepo
) : ViewModel() {
    companion object {
        const val TAG = "AUTH_VIEWMODEL"
    }

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Remaining)
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()
    fun onEvent(event: AuthEvents) {
        when (event) {
            is AuthEvents.signIn -> signIn(event.signIn)
            is AuthEvents.signUp -> signUp(event.signUp)
        }
    }

    private fun signIn(signIn: SignIn) {
        _authState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                val response = authRepo.signIn(signIn)
                Log.d(TAG, "signIn: $response")
                if (response != null) {
                    _authState.value = AuthUiState.Success
                    sessionManager.saveUser(
                        User(
                            id = response.id,
                            email = response.email,
                            token = response.token,
                            name = response.name
                        )
                    )
                } else {
                    _authState.value = AuthUiState.Error("Response is null")
                }
            } catch (e: IOException) {
                _authState.value = e.message?.let { AuthUiState.Error(it) }
                    .run { AuthUiState.Error("Internal error") }
            }
        }
    }

    private fun signUp(signUp: SignUp) {
        _authState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                val response = authRepo.signUp(signUp)
                Log.d(TAG, "signIn: $response")
                if (response != null) {
                    _authState.value = AuthUiState.Success
                    sessionManager.saveUser(
                        User(
                            id = response.id,
                            email = response.email,
                            token = response.token,
                            name = response.name
                        )
                    )
                } else {
                    _authState.value = AuthUiState.Error("Response is null")
                }
            } catch (e: IOException) {
                _authState.value = e.message?.let { AuthUiState.Error(it) }
                    .run { AuthUiState.Error("Internal error") }
            }
        }
    }
}