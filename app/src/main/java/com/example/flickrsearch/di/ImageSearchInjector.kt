package com.example.flickrsearch.di

import com.example.flickrsearch.data.ImageSearchRepository
import com.example.flickrsearch.data.ImageSearchRepositoryImpl
import com.example.flickrsearch.service.ImageSearchService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageSearchBindingInjector {

    @Binds
    abstract fun bindsImageSearchRepository(real: ImageSearchRepositoryImpl): ImageSearchRepository

}

@Module
@InstallIn(SingletonComponent::class)
object ImageSearchProviderInjector {

    @Provides
    @Singleton
    @Named("BASE_URL")
    fun providesBaseUrl(): String = "https://api.flickr.com/services/feeds/"

    @Provides
    @Singleton
    fun providesRetrofit(
        @Named("BASE_URL") baseUrl: String
    ): Retrofit =
        Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providesImageSearchService(retrofit: Retrofit): ImageSearchService = retrofit.create(
        ImageSearchService::class.java
    )

}