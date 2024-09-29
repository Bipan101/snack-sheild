package com.example.snackshield.feature_auth.di

import com.example.snackshield.feature_auth.data.network.AuthApi
import com.example.snackshield.feature_auth.data.repo.AuthRepoImpl
import com.example.snackshield.feature_auth.domain.repo.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun providesAuthApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient): AuthApi {
        return retrofitBuilder.client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build().create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(authApi: AuthApi): AuthRepo {
        return AuthRepoImpl(authApi = authApi)
    }
}