package com.example.a19100520pc02moviles.data.remote

data class DrawCardsResponse(
    val success: Boolean,
    val deck_id: String,
    val cards: List<CardDto>,
    val remaining: Int
)
