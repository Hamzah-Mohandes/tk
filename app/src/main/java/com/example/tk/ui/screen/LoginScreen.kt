package com.example.tk.ui.screen

import androidx.compose.foundation.layout.* // 1. WICHTIG: Layout-Komponenten (Column, Modifier, Padding)
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack // 2. Standard-Icons
import androidx.compose.material3.* // 3. WICHTIG: Material3-Komponenten (TextField, Button, Text etc.)
import androidx.compose.runtime.* // 4. WICHTIG: State-Management (remember, mutableStateOf)
import androidx.compose.ui.Alignment // 5. Für Ausrichtung von Elementen
import androidx.compose.ui.Modifier // 6. WICHTIG: Layout-Modifikatoren
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation // 7. Passwort-Maskierung
import androidx.compose.ui.tooling.preview.Preview // 8. Vorschau-Funktion
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // 9. WICHTIG: ViewModel-Integration
import androidx.navigation.NavController // 10. WICHTIG: Navigation
import androidx.navigation.compose.rememberNavController // 11. Für Preview
import com.example.tk.viewmodel.LoginViewModel

@Composable // 12. WICHTIG: Markiert UI-Komponente
fun LoginScreen(
    navController: NavController, // 10. Navigation zwischen Screens
    viewModel: LoginViewModel = viewModel() // 9. ViewModel für Logik
) {
    // App-spezifische Farbe (sollte in Theme.kt definiert sein)
    val tkBlue = Color(0xFF0061A5)

    // 4. State-Management: Formularfelder und Ladezustand
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // 1. Hauptlayout: Zentrierte Spalte
    Column(
        modifier = Modifier
            .fillMaxSize() // Füllt gesamten Bildschirm
            .padding(16.dp), // Innerer Abstand
        horizontalAlignment = Alignment.CenterHorizontally, // 5. Zentriert Elemente horizontal
        verticalArrangement = Arrangement.Center // Zentriert Elemente vertikal
    ) {
        // Zurück-Button (optional, falls Login nicht Startscreen ist)
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Zurück",
                tint = tkBlue
            )
        }

        // Überschrift
        Text(
            "Anmelden",
            style = MaterialTheme.typography.headlineMedium,
            color = tkBlue,
            modifier = Modifier.padding(bottom = 32.dp) // Abstand nach unten
        )

        // E-Mail-Eingabefeld (80% aller Formulare brauchen nur dies)
        OutlinedTextField(
            value = email, // 4. Gebundener State
            onValueChange = { email = it }, // 4. Aktualisiert State bei Eingabe
            label = { Text("E-Mail") },
            modifier = Modifier
                .fillMaxWidth() // Volle Breite
                .padding(bottom = 16.dp), // Abstand zum nächsten Element
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tkBlue, // 3. Akzentfarbe bei Fokus
                focusedLabelColor = tkBlue
            )
        )

        // Passwort-Eingabefeld (wie E-Mail, aber mit Maskierung)
        OutlinedTextField(
            value = password, // 4. Gebundener State
            onValueChange = { password = it }, // 4. Aktualisiert State
            label = { Text("Passwort") },
            visualTransformation = PasswordVisualTransformation(), // 7. Maskiert Passwort
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = tkBlue,
                focusedLabelColor = tkBlue
            )
        )

        // Anmelde-Button (kritische Aktion)
        Button(
            onClick = {
                isLoading = true // 4. Ladezustand aktivieren
                // 9. Login-Logik im ViewModel aufrufen
                val loginSuccessful = viewModel.login(email, password)
                if (loginSuccessful) {
                    // 10. Navigation zum Dashboard (mit Backstack-Bereinigung)
                    navController.navigate("dashboard") {
                        popUpTo("login") { inclusive = true } // Löscht Login aus Backstack
                    }
                } else {
                    // TODO: Fehlermeldung anzeigen (z. B. Snackbar)
                }
                isLoading = false // Ladezustand zurücksetzen
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = tkBlue // 3. Primärfarbe
            ),
            // 4. Button nur aktiv, wenn Felder ausgefüllt und nicht läd
            enabled = email.isNotBlank() && password.isNotBlank() && !isLoading
        ) {
            // Bedingte Anzeige: Ladeindikator oder Text
            if (isLoading) {
                CircularProgressIndicator(color = Color.White) // 3. Ladeanimation
            } else {
                Text("Anmelden")
            }
        }

        // Passwort vergessen (sekundäre Aktion)
        TextButton(
            onClick = { /* TODO: Passwort-zurücksetzen-Flow */ },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                "Passwort vergessen?",
                color = tkBlue // 3. Akzentfarbe
            )
        }
    }
}

// 8. Vorschau-Funktion (ermöglicht Design ohne App-Start)
@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(navController = rememberNavController()) // 11. Mock-NavController
}
