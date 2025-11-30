package com.example.a19100520pc02moviles.data.remote

data class DeckDto(
    val success: Boolean,
    val deck_id: String,
    val remaining: Int,
    val shuffled: Boolean
)
