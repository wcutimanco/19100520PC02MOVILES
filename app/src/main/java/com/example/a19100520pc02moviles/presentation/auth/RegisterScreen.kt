package com.example.appdpa.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController


@Composable
fun RegisterScreen(navController: NavController){
    var name by remember { mutableStateOf(value="") }
    var email by remember { mutableStateOf(value="") }
    var password by remember { mutableStateOf(value="") }
    var confirmPassword by remember { mutableStateOf(value="") }


Column(
    modifier = Modifier.padding(16.dp)
){
    Spacer(modifier = Modifier.height(16.dp))
    Text(text = "Registro de usuario", style = MaterialTheme.typography.titleLarge)
    OutlinedTextField(
        value = name,
        onValueChange = { name = it },
        label = { Text("Nombre") },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
    )
    OutlinedTextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Correo electrónico") },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
    )
    OutlinedTextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Contraseña") },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation()
    )
    OutlinedTextField(
        value = confirmPassword,
        onValueChange = { confirmPassword = it },
        label = { Text("Confirmar contraseña") },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth(),
        visualTransformation = PasswordVisualTransformation()
    )
    //Button for register
    Button(
        onClick = {
            if(name.isNotBlank()
                && password.isNotBlank()
                && password == confirmPassword){
                navController.navigate("login")
            }
        },
        modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()
    ){
        Text(text = "Registrarse")

    }
}
}