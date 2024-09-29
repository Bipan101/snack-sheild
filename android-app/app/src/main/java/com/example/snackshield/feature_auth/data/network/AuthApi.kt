package com.example.snackshield.feature_auth.data.network

import com.example.snackshield.feature_auth.data.model.DetailDataDto
import com.example.snackshield.feature_auth.data.model.DetailsDto
import com.example.snackshield.feature_auth.data.model.DetailsResponseDto
import com.example.snackshield.feature_auth.data.model.SignInDto
import com.example.snackshield.feature_auth.data.model.SignUpDto
import com.example.snackshield.feature_auth.data.model.UserCreationResponseDto
import com.example.snackshield.feature_auth.data.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("users")
    suspend fun signIn(@Body signIn: SignInDto): Response<UserDto>

    @POST("users")
    suspend fun signUp(@Body signUp: SignUpDto): Response<UserCreationResponseDto>

    @POST("user-details")
    suspend fun userDetails(@Body userDetails : DetailsDto ) : Response<DetailsResponseDto>
}