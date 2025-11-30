package com.example.a19100520pc02moviles.data.repository

import com.example.a19100520pc02moviles.data.remote.DeckApi
import com.example.a19100520pc02moviles.domain.model.Card
import com.example.a19100520pc02moviles.domain.repository.DeckRepository
import javax.inject.Inject

class DeckRepositoryImpl @Inject constructor(
    private val api: DeckApi
) : DeckRepository {
    override suspend fun createDeck(): String {
        return api.shuffleDeck().deckId
    }

    override suspend fun drawCards(deckId: String, count: Int): List<Card> {
        return api.drawCards(deckId, count).cards.map { dto ->
            Card(
                code = dto.code,
                image = dto.image,
                value = dto.value,
                suit = dto.suit
            )
        }
    }
}
