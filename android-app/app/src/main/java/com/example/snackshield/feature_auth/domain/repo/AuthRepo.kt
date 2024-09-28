package com.example.snackshield.feature_auth.domain.repo

import com.example.snackshield.feature_auth.domain.model.Detail
import com.example.snackshield.feature_auth.domain.model.DetailData
import com.example.snackshield.feature_auth.domain.model.SignIn
import com.example.snackshield.feature_auth.domain.model.SignUp
import com.example.snackshield.feature_auth.domain.model.User
import com.example.snackshield.feature_auth.domain.model.UserData

interface AuthRepo {
    suspend fun signIn(signIn: SignIn): User?
    suspend fun signUp(signUp: SignUp): UserData?
    suspend fun userDetails(detail: Detail) : DetailData?
}