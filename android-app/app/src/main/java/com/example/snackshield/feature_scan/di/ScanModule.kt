package com.example.snackshield.feature_scan.di

import com.example.snackshield.feature_scan.data.network.ScanApi
import com.example.snackshield.feature_scan.data.repo.ScanRepoImpl
import com.example.snackshield.feature_scan.domain.repo.ScanRepo
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
object ScanModule {

    @Provides
    @Singleton
    fun provideScanApi(retrofitBuilder : Builder,okHttpClient: OkHttpClient) : ScanApi {
        return retrofitBuilder.client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build().create(ScanApi::class.java)
    }

    @Provides
    @Singleton
    fun provideScanImpl(scanApi: ScanApi) : ScanRepo {
        return ScanRepoImpl(scanApi)
    }
}