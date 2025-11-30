package com.example.a19100520pc02moviles.presentation.juego

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a19100520pc02moviles.domain.model.Card
import com.example.a19100520pc02moviles.domain.repository.DeckRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class JuegoState(
    val deckId: String? = null,
    val playerCards: List<Card> = emptyList(),
    val playerScore: Int = 0,
    val machineScore: Int = 0,
    val winner: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class JuegoViewModel @Inject constructor(
    private val deckRepository: DeckRepository
) : ViewModel() {

    private val _state = MutableStateFlow(JuegoState())
    val state = _state.asStateFlow()

    fun onEvent(event: JuegoEvent) {
        when (event) {
            is JuegoEvent.OnNewGame -> {
                createNewDeck()
            }
            is JuegoEvent.OnDrawCards -> {
                drawCards(event.count)
            }
        }
    }

    private fun createNewDeck() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            deckRepository.createNewDeck().onSuccess { newDeckId ->
                _state.update {
                    it.copy(
                        deckId = newDeckId,
                        machineScore = Random.nextInt(16, 22),
                        playerCards = emptyList(),
                        playerScore = 0,
                        winner = null,
                        isLoading = false
                    )
                }
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun drawCards(count: Int) {
        val deckId = _state.value.deckId ?: return

        if (_state.value.winner != null) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            deckRepository.drawCards(deckId, count).onSuccess { cards ->
                val newPlayerScore = calculateScore(cards)
                _state.update {
                    it.copy(
                        playerCards = cards,
                        playerScore = newPlayerScore,
                        isLoading = false
                    )
                }
                determineWinner()
            }.onFailure { error ->
                _state.update {
                    it.copy(
                        error = error.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun calculateScore(cards: List<Card>): Int {
        var score = 0
        var aces = 0
        for (card in cards) {
            when (card.value) {
                "ACE" -> {
                    aces++
                    score += 11
                }
                "KING", "QUEEN", "JACK" -> score += 10
                else -> score += card.value.toIntOrNull() ?: 0
            }
        }
        while (score > 21 && aces > 0) {
            score -= 10
            aces--
        }
        return score
    }

    private fun determineWinner() {
        val playerScore = _state.value.playerScore
        val machineScore = _state.value.machineScore

        val winner = if (playerScore > 21) {
            "Máquina"
        } else if (playerScore == 21 && _state.value.playerCards.size == 2) {
            "Jugador (Blackjack!)"
        } else if (playerScore > machineScore) {
            "Jugador"
        } else if (machineScore > playerScore) {
            "Máquina"
        } else {
            "Empate"
        }
        _state.update { it.copy(winner = winner) }
    }
}

sealed class JuegoEvent {
    object OnNewGame : JuegoEvent()
    data class OnDrawCards(val count: Int) : JuegoEvent()
}
