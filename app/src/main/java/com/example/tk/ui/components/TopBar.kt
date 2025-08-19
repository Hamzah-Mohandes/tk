package com.example.tkapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // 1. WICHTIG: Box, Modifier, Padding
import androidx.compose.foundation.shape.CircleShape // 2. Für kreisförmige Elemente
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // 3. Standard-Icons (Menu, Search, Person, Email)
import androidx.compose.material3.* // 4. WICHTIG: TopAppBar, IconButton, Text
import androidx.compose.runtime.Composable // 5. WICHTIG: Composable-Funktion
import androidx.compose.ui.Alignment // 6. Für Ausrichtung
import androidx.compose.ui.Modifier // 7. WICHTIG: Layout-Modifikatoren
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable // 5. Markiert UI-Komponente
fun TopBar(
    isLoggedIn: Boolean, // 8. WICHTIG: Steuert die UI basierend auf Login-Status
    username: String? = null, // 8. Benutzername für Avatar (optional)
    onMenuClick: () -> Unit = {}, // 9. WICHTIG: Callback für Menü-Klick
    onSearchClick: () -> Unit = {}, // 9. Callback für Such-Klick
    onProfileClick: () -> Unit = {}, // 9. WICHTIG: Callback für Profil-Klick
    onMailClick: () -> Unit = {} // 9. Callback für E-Mail-Klick (unbenutzt)
) {
    // App-spezifische Farbe (sollte in Theme.kt definiert sein)
    val tkBlue = Color(0xFF0061A5)

    // 4. TopAppBar als Hauptcontainer
    TopAppBar(
        title = { Text("TK-App") }, // App-Titel
        navigationIcon = {
            // 3. & 4. Menü-Button links
            IconButton(onClick = onMenuClick) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Menü",
                    tint = Color.White // Weiße Icon-Farbe
                )
            }
        },
        actions = { // 4. Aktions-Icons rechts
            // Such-Icon
            IconButton(onClick = onSearchClick) {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Suche",
                    tint = Color.White
                )
            }

            // Profil-Icon oder Avatar (dynamisch basierend auf Login-Status)
            IconButton(onClick = onProfileClick) {
                if (isLoggedIn && username != null) {
                    // 1. & 2. Avatar mit Initialen (für eingeloggte Benutzer)
                    Box(
                        modifier = Modifier
                            .size(32.dp) // Feste Größe
                            .background(Color.White, CircleShape) // 2. Kreisförmiger weißer Hintergrund
                            .padding(4.dp), // Innerer Abstand
                        contentAlignment = Alignment.Center // 6. Zentriert Inhalt
                    ) {
                        Text(
                            text = username.take(1).uppercase(), // 8. Erster Buchstabe des Usernames
                            color = tkBlue, // 8. TK-Blau als Textfarbe
                            fontWeight = FontWeight.Bold // Fett gedruckt
                        )
                    }
                } else {
                    // 3. Standard-Profil-Icon (für nicht eingeloggte Benutzer)
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Anmelden",
                        tint = Color.White
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = tkBlue, // 8. Hintergrundfarbe
            titleContentColor = Color.White // Titel-Farbe
        )
    )
}
