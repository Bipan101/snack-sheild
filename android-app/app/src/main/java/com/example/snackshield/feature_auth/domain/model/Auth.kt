package com.example.snackshield.feature_auth.domain.model

import com.example.snackshield.feature_auth.data.model.DetailsDto
import com.example.snackshield.feature_auth.data.model.SignInDto
import com.example.snackshield.feature_auth.data.model.SignUpDto
import kotlinx.serialization.Serializable


data class User(
    val id: String,
    val name: String,
    val email: String,
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


data class UserData(
    val name: String,
    val password: String,
    val email: String,
    val id: String,
    val isActive: Boolean
)

data class Detail(
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: String,
    val allergens: List<String>,
    val userId: String,
    val dietPreference : String
) {
    fun toDetailDto() = DetailsDto(
        age, weight, height, gender, allergens, userId, dietPreference
    )
}

data class DetailData(
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: String,
    val allergens: List<String>,
    val dietPreference: String?, // Nullable type for dietPreference
    val id: String
)