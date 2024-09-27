package com.example.snackshield.feature_auth.data.network

import com.example.snackshield.feature_auth.data.model.SignInDto
import com.example.snackshield.feature_auth.data.model.SignUpDto
import com.example.snackshield.feature_auth.data.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("login/signin")
    suspend fun signIn(@Body signIn: SignInDto): Response<UserDto>

    @POST("login/signup")
    suspend fun signUp(@Body signUp: SignUpDto): Response<UserDto>
}