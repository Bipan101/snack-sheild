package com.example.snackshield.feature_auth.domain.model

import com.example.snackshield.feature_auth.data.model.SignInDto
import com.example.snackshield.feature_auth.data.model.SignUpDto


data class User(
    val id: Int,
    val name: String,
    val email: String,
    val token: String,
)

data class SignIn(
    val email: String,
    val password: String,
) {
    fun toSignInDto() = SignInDto(
        email = email,
        password = password
    )
}

data class SignUp(
    val name: String,
    val email: String,
    val password: String,
) {
    fun toSignUpDto() = SignUpDto(
        name = name,
        email = email,
        password = password
    )
}