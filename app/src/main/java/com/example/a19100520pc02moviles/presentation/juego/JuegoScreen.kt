
package com.example.a19100520pc02moviles.presentation.juego

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.example.a19100520pc02moviles.R

@Composable
fun JuegoScreen() {
    var numCartas by remember { mutableStateOf(2f) }
    var playerScore by remember { mutableStateOf(0) }
    var machineScore by remember { mutableStateOf(0) }
    var winner by remember { mutableStateOf<String?>(null) }

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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                // Placeholder for player cards
            }
            Text("Tu puntaje: $playerScore", color = Color.White, fontSize = 18.sp)

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
                    onClick = { /* TODO: Iniciar juego */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000))
                ) {
                    Text("Iniciar Juego")
                }
                Button(
                    onClick = { /* TODO: Robar cartas */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD700))
                ) {
                    Text("Robar Cartas", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Machine's score
            Text("Puntaje de la máquina: $machineScore", color = Color.White, fontSize = 18.sp)

            // Winner text
            winner?.let {
                Text(
                    "El ganador es: $it",
                    color = Color(0xFF00FF00),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}
