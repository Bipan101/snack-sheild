package com.example.snackshield.feature_auth.data.model

import com.example.snackshield.feature_auth.domain.model.Detail
import com.example.snackshield.feature_auth.domain.model.DetailData
import com.example.snackshield.feature_auth.domain.model.User
import com.example.snackshield.feature_auth.domain.model.UserData
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String = "",
) {
    fun toUser() = User(
        id, name, email
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

@Serializable
data class UserCreationResponseDto(
    val data: UserDataDto,
    val message: String
)

@Serializable
data class UserDataDto(
    val name: String,
    val password: String,
    val email: String,
    val id: String,
    val isActive: Boolean
) {
    fun toUserData() = UserData(
        name,password,email,id,isActive
    )
}

@Serializable
data class DetailsDto(
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: String,
    val allergens: List<String>,
    val userId: String,
    val dietPreference : String
)


@Serializable
data class DetailsResponseDto(
    val data: DetailDataDto,
    val message: String
)

@Serializable
data class DetailDataDto(
    val user: UserIdDto,
    val age: Int,
    val weight: Int,
    val height: Int,
    val gender: String,
    val allergens: List<String>,
    val dietPreference: String?, // Nullable type for dietPreference
    val id: String
) {
    fun toDetails() = DetailData(
        age,weight,height,gender,allergens,dietPreference,id
    )
}
@Serializable
data class UserIdDto (
    val id : String,
)
