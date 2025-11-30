package com.example.a19100520pc02moviles.data.repository

import com.example.a19100520pc02moviles.data.mapper.toDomain
import com.example.a19100520pc02moviles.data.remote.DeckOfCardsApi
import com.example.a19100520pc02moviles.domain.model.Card
import com.example.a19100520pc02moviles.domain.repository.DeckRepository
import javax.inject.Inject

class DeckRepositoryImpl @Inject constructor(
    private val api: DeckOfCardsApi
) : DeckRepository {

    override suspend fun createNewDeck(): Result<String> {
        return try {
            val response = api.createNewDeck()
            if (response.success) {
                Result.success(response.deck_id)
            } else {
                Result.failure(Exception("Failed to create new deck"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun drawCards(deckId: String, count: Int): Result<List<Card>> {
        return try {
            val response = api.drawCards(deckId, count)
            if (response.success) {
                Result.success(response.cards.map { it.toDomain() })
            } else {
                Result.failure(Exception("Failed to draw cards"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
