package com.example.snackshield.feature_auth.data.repo

import com.example.snackshield.feature_auth.data.network.AuthApi
import com.example.snackshield.feature_auth.domain.model.SignIn
import com.example.snackshield.feature_auth.domain.model.SignUp
import com.example.snackshield.feature_auth.domain.model.User
import com.example.snackshield.feature_auth.domain.repo.AuthRepo
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepo {
    override suspend fun signIn(signIn: SignIn): User? {
        val userDto = authApi.signIn(signIn.toSignInDto())
        return if (userDto.isSuccessful) {
            userDto.body()!!.toUser()
        } else {
            null
        }
    }

    override suspend fun signUp(signUp: SignUp): User? {
        val userDto = authApi.signUp(signUp.toSignUpDto())
        return if (userDto.isSuccessful) {
            userDto.body()!!.toUser()
        } else {
            null
        }
    }

}