package com.example.snackshield.common.data.network

import com.example.snackshield.common.domain.repo.SessionManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val sessionManager: SessionManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        val token = runBlocking { sessionManager.getUser()?.token }
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}