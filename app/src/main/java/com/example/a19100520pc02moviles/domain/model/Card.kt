package com.example.a19100520pc02moviles.domain.model

data class Card(
    val code: String,
    val image: String,
    val value: String,
    val suit: String
) {
    val numericValue: Int
        get() = when (value) {
            "ACE" -> 11
            "KING", "QUEEN", "JACK", "10" -> 10
            else -> value.toIntOrNull() ?: 0
        }
}
