package com.example.snackshield.common.data.repo

import android.content.Context
import com.example.snackshield.common.domain.repo.SessionManager
import com.example.snackshield.feature_auth.domain.model.User
import javax.inject.Inject

class SessionManagerImpl @Inject constructor(
    context: Context,
) : SessionManager {
    private val sharedPreferences =
        context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)

    companion object {
        private const val USER_ID = "id"
        private const val USER_NAME = "name"
        private const val USER_EMAIL = "email"
        private const val USER_PROFILE = "profile"
        private const val USER_TOKEN = "token"
    }

    override fun getUser(): User? {
        val userName = sharedPreferences.getString(USER_NAME, null)
        val userEmail = sharedPreferences.getString(USER_EMAIL, null)
        val userToken = sharedPreferences.getString(USER_TOKEN, null)
        val userIdString = sharedPreferences.getString(USER_ID, null)
        val userProfile = sharedPreferences.getString(USER_PROFILE, null)
        return if (userName != null && userEmail != null && userIdString != null && userToken != null) {
            try {
                val userId = userIdString.toInt()
                User(id = userId, name = userName, email = userEmail, token = userToken)
            } catch (e: NumberFormatException) {
                null
            }
        } else {
            null
        }
    }


    override suspend fun saveUser(user: User) {
        sharedPreferences.edit().apply {
            putString(USER_ID, user.id.toString())
            putString(USER_NAME, user.name)
            putString(USER_EMAIL, user.email)
            putString(USER_PROFILE, "")
            putString(USER_TOKEN, user.token)
            apply()
        }
    }

    override suspend fun updateUser(user: User) {
        sharedPreferences.edit().apply {
            putString(USER_ID, user.id.toString())
            putString(USER_NAME, user.name)
            putString(USER_EMAIL, user.email)
            putString(USER_PROFILE, "")
            putString(USER_TOKEN, user.token)
            apply()
        }
    }

    override suspend fun clearUser() {
        sharedPreferences.edit().clear().apply()
    }

}