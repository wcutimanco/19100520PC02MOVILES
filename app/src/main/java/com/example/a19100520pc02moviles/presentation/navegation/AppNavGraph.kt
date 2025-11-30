package com.example.a19100520pc02moviles.presentation.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.a19100520pc02moviles.presentation.juego.JuegoScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "juego"){
        composable("juego"){ JuegoScreen() }
    }
}
