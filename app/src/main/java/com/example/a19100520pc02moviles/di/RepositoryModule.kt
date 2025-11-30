package com.example.a19100520pc02moviles.di

import com.example.a19100520pc02moviles.data.repository.DeckRepositoryImpl
import com.example.a19100520pc02moviles.domain.repository.DeckRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDeckRepository(impl: DeckRepositoryImpl): DeckRepository
}
