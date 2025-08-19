package com.example.tk.ui.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult // 1. WICHTIG: Activity-Ergebnisse verarbeiten (z.B. Bildauswahl)
import androidx.activity.result.contract.ActivityResultContracts // 1. Vertrag für Bildauswahl
import androidx.compose.foundation.Image // 2. Bildanzeige
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable // 3. WICHTIG: Klickbare Elemente
import androidx.compose.foundation.layout.* // 4. WICHTIG: Layout-Komponenten (Column, Box, Spacer etc.)
import androidx.compose.foundation.shape.CircleShape // 5. Form für kreisförmige Elemente
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.* // 6. WICHTIG: Material3 UI-Komponenten (Scaffold, Button, Text etc.)
import androidx.compose.runtime.* // 7. WICHTIG: State-Management (remember, mutableStateOf)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier // 8. WICHTIG: Layout-Modifikatoren
import androidx.compose.ui.draw.clip // 9. Zum Beschneiden von Bildern/Elementen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController // 10. WICHTIG: Navigation zwischen Screens
import coil.compose.rememberImagePainter // 11. Bildlader (Coil-Bibliothek)
import com.example.tk.viewmodel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel // 12. WICHTIG: ViewModel-Integration

@OptIn(ExperimentalMaterial3Api::class)
@Composable // 13. WICHTIG: Markiert UI-Komponente
fun ProfileScreen(
    navController: NavController, // 10. Navigation zwischen Screens
    loginViewModel: LoginViewModel = viewModel() // 12. ViewModel für Logik/Daten
) {
    // 7. State-Management: Beobachte ViewModel-Daten
    val username by loginViewModel.username.collectAsState()
    val email by loginViewModel.email.collectAsState()

    // 7. Lokaler State für Bild-URI (nur in diesem Screen relevant)
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // 1. Activity Result Launcher für Bildauswahl (80% der Bildauswahl-Logik)
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent() // Standardvertrag für Dateiauswahl
    ) { uri: Uri? ->
        uri?.let { imageUri = it } // Aktualisiere State bei Auswahl
    }

    // App-spezifische Farbe (sollte idealerweise in Theme.kt definiert sein)
    val tkBlue = Color(0xFF0061A5)

    // 6. Scaffold als Grundgerüst (TopBar + Inhalt)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mein Profil") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // 10. Navigation: Zurück
                        Icon(Icons.Default.ArrowBack, "Zurück")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0061A5),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding) // Berücksichtigt Scaffold-Padding
                .padding(16.dp) // Innerer Abstand
        ) {
            // PROFILBILD-BEREICH (20% des Codes, 80% der visuellen Wirkung)
            Box(
                modifier = Modifier
                    .size(120.dp) // Feste Größe
                    .clip(CircleShape) // 5. Kreisförmig beschneiden
                    .background(Color.LightGray) // Platzhalter-Hintergrund
                    .clickable { imagePicker.launch("image/*") } // 3. Öffnet Bildauswahl bei Klick
                    .border(2.dp, Color(0xFF0061A5), CircleShape) // Blau umrandet
                    .align(Alignment.CenterHorizontally), // Zentriert
                contentAlignment = Alignment.Center // Inhalt zentrieren
            ) {
                // Bedingte Anzeige: Bild ODER Initialen
                imageUri?.let { uri ->
                    // 2. & 11. Lade und zeige Bild mit Coil
                    Image(
                        painter = rememberImagePainter(uri),
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape) // 5. Kreisförmig beschneiden
                    )
                } ?: run {
                    // Fallback: Erster Buchstabe des Usernames oder "?"
                    Text(
                        text = username?.take(1)?.uppercase() ?: "?",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                }
            }

            // "Foto ändern"-Text (alternativer Weg zur Bildauswahl)
            Text(
                text = "Foto ändern",
                color = Color(0xFF0061A5),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 8.dp)
                    .clickable { imagePicker.launch("image/*") } // 3. Klick-Aktion
            )

            Spacer(modifier = Modifier.height(24.dp)) // Abstandhalter

            // PROFILINFORMATIONEN (wiederverwendbare Komponente)
            ProfileItem("Benutzername", username ?: "Nicht eingeloggt")
            ProfileItem("E-Mail", email ?: "Nicht verfügbar")

            Spacer(modifier = Modifier.weight(1f)) // Drückt Button nach unten

            // ABMELDE-BUTTON (kritische Aktion)
            Button(
                onClick = {
                    loginViewModel.logout() // ViewModel-Logik
                    navController.navigate("login") { // 10. Navigation zum Login
                        popUpTo(0) { inclusive = true } // Löscht den gesamten Backstack
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = tkBlue, // Primärfarbe
                    contentColor = Color.White
                )
            ) {
                Text("Abmelden")
            }
        }
    }
}

// WIEDERVERWENDBARE KOMPONENTE (80% der Profil-Items mit 20% Code)
@Composable
private fun ProfileItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray // Sekundäre Farbe für Label
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 4.dp)
        )
        Divider( // Trennlinie für bessere Lesbarkeit
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.LightGray.copy(alpha = 0.3f)
        )
    }
}
