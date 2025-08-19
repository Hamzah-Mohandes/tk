package com.example.tk.ui.screen

import android.R.color.white
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // Box, Column, Modifier, Padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.* // Scaffold, TopAppBar, Text, Icon, IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // Für Layout-Modifikatoren
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp // Für Dichte-unabhängige Pixel
import androidx.compose.ui.unit.sp // Für skalierbare Schriftgrößen
import androidx.navigation.NavController // Für Navigation zwischen Screens
import com.example.tk.domain.model.Message // Datenmodell für Nachrichten

@OptIn(ExperimentalMaterial3Api::class) // Erfordert für Scaffold/TopAppBar in Material3
@Composable // Markiert die Funktion als Jetpack Compose UI-Komponente
fun MessageDetailScreen(
    navController: NavController, // Steuert die Navigation zwischen Screens
    message: Message // Enthält die anzuzeigenden Nachrichtendaten (Titel, Datum, Inhalt)
) {
    // Primärfarbe der App (TK-Blau)
    val tkBlue = Color(0xFF0061A5)

    // Standard-Layoutstruktur mit App-Bar und Inhalt
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Nachricht", // Titel der App-Bar
                        color = tkBlue // Textfarbe des Titels
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) { // Zurück-Button mit Navigation
                        Icon(
                            Icons.Default.ArrowBack, // Standard-Zurück-Pfeil
                            contentDescription = "Zurück", // Barrierefreiheit: Beschreibung für Screenreader
                            tint = Color.White // Icon-Farbe
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = tkBlue, // Hintergrundfarbe der App-Bar
                    titleContentColor = Color.White // Farbe des Titels
                )
            )
        }
    ) { innerPadding -> // Automatisch berechnetes Padding für System-Bars (Statusbar, etc.)
        // Hauptcontainer mit blauem Hintergrund
        Box(
            modifier = Modifier
                .fillMaxSize() // Füllt den gesamten verfügbaren Platz
                .background(tkBlue) // Hintergrundfarbe
                .padding(innerPadding) // Berücksichtigt das Padding vom Scaffold
                .padding(16.dp) // Innerer Abstand (16dp auf allen Seiten)
        ) {
            // Vertikale Anordnung der Textelemente
            Column {
                // Titel der Nachricht
                Text(
                    text = message.title, // Titel aus dem Message-Objekt
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White), // Überschrift-Stil mit weißer Farbe
                    modifier = Modifier.padding(bottom = 16.dp) // Abstand nach unten
                )

                // Datum der Nachricht
                Text(
                    text = message.date, // Datum aus dem Message-Objekt
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.7f) // Halbtransparentes Weiß für sekundären Text
                    ),
                    modifier = Modifier.padding(bottom = 16.dp) // Abstand nach unten
                )

                // Inhalt der Nachricht
                Text(
                    text = message.content, // Inhalt aus dem Message-Objekt
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White), // Standardtext-Stil mit weißer Farbe
                    lineHeight = 24.sp // Zeilenhöhe für bessere Lesbarkeit
                )
            }
        }
    }
}
