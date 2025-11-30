package com.example.a19100520pc02moviles.presentation.juego

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.a19100520pc02moviles.R

@Composable
fun JuegoScreen(
    viewModel: JuegoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var numCartas by remember { mutableStateOf(2f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_casino),
                contentDescription = "Casino Logo",
                modifier = Modifier.height(100.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Casino Royale",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFD700)
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Player's section
            Text("Tus Cartas", color = Color.White, fontSize = 20.sp)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                items(state.playerCards) {
                    AsyncImage(model = it.image, contentDescription = null, modifier = Modifier.height(150.dp))
                }
            }
            Text("Tu puntaje: ${state.playerScore}", color = Color.White, fontSize = 18.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // Card selection
            Text("¿Cuántas cartas quieres?", color = Color.White)
            Slider(
                value = numCartas,
                onValueChange = { numCartas = it },
                valueRange = 2f..5f,
                steps = 2,
                colors = SliderDefaults.colors(
                    thumbColor = Color(0xFFFFD700),
                    activeTrackColor = Color(0xFFFFD700),
                    inactiveTrackColor = Color.Gray
                )
            )
            Text(numCartas.toInt().toString(), color = Color.White)

            Spacer(modifier = Modifier.height(32.dp))

            // Action buttons
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(
                    onClick = { viewModel.onEvent(JuegoEvent.OnNewGame) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                ) {
                    Text("Iniciar Juego")
                }
                Button(
                    onClick = { viewModel.onEvent(JuegoEvent.OnDrawCards(numCartas.toInt())) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700)),
                    enabled = state.deckId != null && state.winner == null
                ) {
                    Text("Robar Cartas", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Machine's score
            Text("Puntaje de la máquina: ${state.machineScore}", color = Color.White, fontSize = 18.sp)

            // Winner text
            state.winner?.let {
                Text(
                    "El ganador es: $it",
                    color = if (it == "Jugador") Color(0xFF00FF00) else Color.Red,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top=16.dp), color = Color(0xFFFFD700))
            }

            state.error?.let {
                Text(
                    "Error: $it",
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
