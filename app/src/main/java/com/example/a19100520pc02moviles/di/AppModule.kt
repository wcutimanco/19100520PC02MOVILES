package com.example.a19100520pc02moviles.di

import com.example.a19100520pc02moviles.data.remote.DeckOfCardsApi
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
    fun provideDeckOfCardsApi(): DeckOfCardsApi {
        return Retrofit.Builder()
            .baseUrl("https://deckofcardsapi.com/api/deck/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeckOfCardsApi::class.java)
    }
}
