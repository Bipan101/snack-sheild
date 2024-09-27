package com.example.snackshield.common.di

import android.content.Context
import com.example.snackshield.common.constants.NetworkConstants
import com.example.snackshield.common.data.network.AuthInterceptor
import com.example.snackshield.common.data.repo.SessionManagerImpl
import com.example.snackshield.common.domain.repo.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManagerImpl(context = context)
    }

    @Provides
    @Singleton
    fun providesRetrofitBuilder(): Builder {
        return Builder().baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }
}