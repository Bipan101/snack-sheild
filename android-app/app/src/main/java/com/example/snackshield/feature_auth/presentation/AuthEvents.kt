package com.example.snackshield.feature_auth.presentation

import com.example.snackshield.feature_auth.domain.model.SignIn
import com.example.snackshield.feature_auth.domain.model.SignUp

sealed interface AuthEvents {
    data class signIn(val signIn: SignIn) : AuthEvents
    data class signUp(val signUp: SignUp) : AuthEvents
}