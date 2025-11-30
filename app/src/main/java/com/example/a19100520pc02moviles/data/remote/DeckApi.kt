package com.example.a19100520pc02moviles.data.remote

import com.example.a19100520pc02moviles.data.remote.dto.DeckResponse
import com.example.a19100520pc02moviles.data.remote.dto.DrawCardResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeckApi {
    @GET("deck/new/shuffle/")
    suspend fun shuffleDeck(@Query("deck_count") deckCount: Int = 1): DeckResponse

    @GET("deck/{deck_id}/draw/")
    suspend fun drawCards(
        @Path("deck_id") deckId: String,
        @Query("count") count: Int
    ): DrawCardResponse
}
