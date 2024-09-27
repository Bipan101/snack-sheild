package com.example.snackshield.feature_auth.di

import com.example.snackshield.feature_auth.data.network.AuthApi
import com.example.snackshield.feature_auth.data.repo.AuthRepoImpl
import com.example.snackshield.feature_auth.domain.repo.AuthRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit.Builder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun providesAuthApi(retrofitBuilder: Builder): AuthApi {
        return retrofitBuilder.build().create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepo(authApi: AuthApi): AuthRepo {
        return AuthRepoImpl(authApi = authApi)
    }
}