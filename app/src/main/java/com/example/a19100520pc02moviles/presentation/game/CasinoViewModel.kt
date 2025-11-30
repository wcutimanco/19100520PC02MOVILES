package com.example.a19100520pc02moviles.presentation.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a19100520pc02moviles.domain.model.Card
import com.example.a19100520pc02moviles.domain.repository.DeckRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class CasinoState(
    val isLoading: Boolean = false,
    val deckId: String? = null,
    val playerCards: List<Card> = emptyList(),
    val playerScore: Int = 0,
    val machineScore: Int = 0,
    val gameStatus: GameStatus = GameStatus.IDLE, // IDLE, CHOOSING_COUNT, RESULT
    val resultMessage: String = "",
    val error: String? = null
)

enum class GameStatus {
    IDLE,
    CHOOSING_COUNT,
    RESULT
}

@HiltViewModel
class CasinoViewModel @Inject constructor(
    private val repository: DeckRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CasinoState())
    val state: StateFlow<CasinoState> = _state.asStateFlow()

    init {
        startNewGame()
    }

    fun startNewGame() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null, gameStatus = GameStatus.IDLE, playerCards = emptyList(), playerScore = 0, machineScore = 0, resultMessage = "") }
            try {
                val deckId = repository.createDeck()
                val machineScore = Random.nextInt(16, 22) // 16 to 21
                _state.update {
                    it.copy(
                        isLoading = false,
                        deckId = deckId,
                        machineScore = machineScore,
                        gameStatus = GameStatus.CHOOSING_COUNT
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Error creating deck: ${e.localizedMessage}") }
            }
        }
    }

    fun drawCards(count: Int) {
        val deckId = state.value.deckId ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val cards = repository.drawCards(deckId, count)
                val playerScore = calculateScore(cards)
                val machineScore = state.value.machineScore

                val result = determineWinner(playerScore, machineScore)

                _state.update {
                    it.copy(
                        isLoading = false,
                        playerCards = cards,
                        playerScore = playerScore,
                        resultMessage = result,
                        gameStatus = GameStatus.RESULT
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = "Error drawing cards: ${e.localizedMessage}") }
            }
        }
    }

    private fun calculateScore(cards: List<Card>): Int {
        var score = cards.sumOf { it.numericValue }
        // Assuming simple rule *A=11 always as per requirement "A = 11"
        // If complex blackjack rules were needed (A=1 or 11), we would handle it here.
        // But requirement says: "*A = 11" strictly.
        return score
    }

    private fun determineWinner(playerScore: Int, machineScore: Int): String {
        // Reglas: Gana el mayor número de los dos y que se acerque más a 21.
        // Implicitly, if > 21, you bust/lose? Requirement doesn't say "Bust", but "Gana el mayor... que se acerque mas a 21".
        // Usually > 21 means 0 or lose.
        // Let's assume standard blackjack proximity:
        // If player > 21, player loses (unless machine also > 21, but machine is 16-21).
        
        if (playerScore > 21) return "Perdiste (Te pasaste de 21)"
        
        return when {
            playerScore > machineScore -> "¡Ganaste!"
            playerScore < machineScore -> "La Máquina Gana"
            else -> "Empate"
        }
    }
}
