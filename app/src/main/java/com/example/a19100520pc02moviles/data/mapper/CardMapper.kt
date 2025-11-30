package com.example.a19100520pc02moviles.data.mapper

import com.example.a19100520pc02moviles.data.remote.CardDto
import com.example.a19100520pc02moviles.domain.model.Card

fun CardDto.toDomain(): Card {
    return Card(
        image = image,
        value = value,
        suit = suit,
        code = code
    )
}
