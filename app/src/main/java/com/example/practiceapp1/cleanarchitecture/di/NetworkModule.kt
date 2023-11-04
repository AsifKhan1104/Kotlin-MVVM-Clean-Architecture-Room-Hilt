package com.example.practiceapp1.cleanarchitecture.di

import com.example.practiceapp1.cleanarchitecture.util.Constants
import com.example.practiceapp1.cleanarchitecture.data.source.network.Remote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Asif Khan on 03/11/23.
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesBaseUrl(): String {
        return Constants.BASE_URL
    }

    @Singleton
    @Provides
    fun providesRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): Remote {
        return retrofit.create(Remote::class.java)
    }
}