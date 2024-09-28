package com.example.snackshield.feature_auth.presentation

import com.example.snackshield.feature_auth.domain.model.Detail
import com.example.snackshield.feature_auth.domain.model.SignIn
import com.example.snackshield.feature_auth.domain.model.SignUp

sealed interface AuthEvents {
    data class UserSignIn(val signIn: SignIn) : AuthEvents
    data class UserSignUp(val signUp: SignUp) : AuthEvents
    data class UserDetail(val detail: Detail) : AuthEvents
    data object GetUserId : AuthEvents
}