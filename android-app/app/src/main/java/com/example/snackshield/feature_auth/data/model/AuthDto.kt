package com.example.snackshield.feature_auth.data.model

import com.example.snackshield.feature_auth.domain.model.User
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String = "",
    @SerializedName("token") val token: String,
    @SerializedName("balance") val balance: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName(" updated_at") val updatedAt: String
) {
    fun toUser() = User(
        id, name, email, token
    )
}

@Serializable
data class SignInDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)

@Serializable
data class SignUpDto(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)
