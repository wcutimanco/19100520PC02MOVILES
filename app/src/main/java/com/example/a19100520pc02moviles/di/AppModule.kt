package com.example.a19100520pc02moviles.di

import com.example.a19100520pc02moviles.data.remote.DeckApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDeckApi(): DeckApi {
        return Retrofit.Builder()
            .baseUrl("https://deckofcardsapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeckApi::class.java)
    }
}
