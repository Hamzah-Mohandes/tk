package com.example.tk.ui.screen

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack // Importiere das ArrowBack Icon
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment // Für die Ausrichtung
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tk.viewmodel.LoginViewModel

@Composable
fun LoginScreen( navController: NavController,
                 viewModel: LoginViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        // Box, um den Zurück-Button und den Titel nebeneinander anzuordnen
        Box(modifier = Modifier.fillMaxWidth()) {
            // Zurück-Button am Anfang der Box (links)
            IconButton(
                onClick = { navController.popBackStack() }, // Aktion zum Zurücknavigieren
                modifier = Modifier.align(Alignment.CenterStart) // Richte den Button links aus
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Standard-Zurück-Pfeil-Icon
                    contentDescription = "Zurück" // Wichtig für Barrierefreiheit
                )
            }
            // Titel in der Mitte der Box
            Text(
                text = "Login",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Center) // Richte den Text in der Mitte aus
            )
        }

        Spacer(modifier = Modifier.height(16.dp)) // Etwas Abstand nach dem Titelbereich

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Benutzername") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Passwort") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.login(username, password)

                if (username.isNotBlank() && password.isNotBlank()) {
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true }
                    }
                } else {
                    errorMessage = "Bitte geben Sie Benutzernamen und Passwort ein!"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        errorMessage?.let {
            Spacer(modifier = Modifier.height(12.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }
    }
}

// Hier ist die Preview-Funktion
@Preview(showBackground = true) // showBackground = true zeigt einen Hintergrund für die Vorschau
@Composable
fun LoginScreenPreview() {
    // Da LoginScreen einen NavController benötigt, erstellen wir hier einen Dummy-NavController
    // für die Vorschau.
    val navController = rememberNavController()
    LoginScreen(navController, viewModel())
}
