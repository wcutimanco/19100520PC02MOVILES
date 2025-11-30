package com.example.a19100520pc02moviles.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DeckResponse(
    val success: Boolean,
    @SerializedName("deck_id") val deckId: String,
    val remaining: Int,
    val shuffled: Boolean
)

data class DrawCardResponse(
    val success: Boolean,
    @SerializedName("deck_id") val deckId: String,
    val cards: List<CardDto>,
    val remaining: Int
)

data class CardDto(
    val code: String,
    val image: String,
    val images: CardImages,
    val value: String,
    val suit: String
)

data class CardImages(
    val svg: String,
    val png: String
)
