package com.example.a19100520pc02moviles.domain.repository

import com.example.a19100520pc02moviles.domain.model.Card

interface DeckRepository {
    suspend fun createDeck(): String
    suspend fun drawCards(deckId: String, count: Int): List<Card>
}
