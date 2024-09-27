package com.example.snackshield.common.domain.repo

import com.example.snackshield.feature_auth.domain.model.User

interface SessionManager {
    fun getUser(): User?
    suspend fun saveUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun clearUser()
}