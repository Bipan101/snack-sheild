package com.example.snackshield.feature_auth.domain.repo

import com.example.snackshield.feature_auth.domain.model.SignIn
import com.example.snackshield.feature_auth.domain.model.SignUp
import com.example.snackshield.feature_auth.domain.model.User

interface AuthRepo {
    suspend fun signIn(signIn: SignIn): User?
    suspend fun signUp(signUp: SignUp): User?
}