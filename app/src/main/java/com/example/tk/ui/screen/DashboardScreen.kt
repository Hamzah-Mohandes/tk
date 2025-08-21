package com.example.tk.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // 1. WICHTIG: Layout-Komponenten (Column, Row, Spacer etc.)
import androidx.compose.foundation.lazy.LazyColumn // 2. WICHTIG: Performante Listen
import androidx.compose.foundation.lazy.items // 2. Für Listen-Items
import androidx.compose.foundation.shape.RoundedCornerShape // 3. Für abgerundete Ecken
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward // 4. Standard-Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.* // 5. WICHTIG: Material3-Komponenten (Scaffold, Card, Text etc.)
import androidx.compose.runtime.* // 6. WICHTIG: State-Management
import androidx.compose.ui.Alignment // 7. Für Ausrichtung
import androidx.compose.ui.Modifier // 8. WICHTIG: Layout-Modifikatoren
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow // 9. Für Text-Überlauf
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel // 10. WICHTIG: ViewModel-Integration
import androidx.navigation.NavController // 11. WICHTIG: Navigation
import com.example.tk.viewmodel.LoginViewModel
import com.example.tk.viewmodel.ReportViewModel
import com.example.tkapp.ui.components.TopBar // 12. Wiederverwendbare Komponente

@Composable
fun DashboardScreen(
    navController: NavController, // 11. Navigation zwischen Screens
    loginViewModel: LoginViewModel = viewModel(), // 10. Login-Status und Benutzerdaten
    reportViewModel: ReportViewModel = viewModel() // 10. Nachrichten-Daten
) {

    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
    val username by loginViewModel.username.collectAsState()
    val messages by reportViewModel.messages.collectAsState()

    // 5. Scaffold als Grundgerüst (TopBar + Inhalt)
    Scaffold(
        topBar = {
            TopBar( // 12. Wiederverwendbare TopBar-Komponente
                isLoggedIn = isLoggedIn,
                username = username,
                onMenuClick = { /* Handle menu click */ },
                onSearchClick = { navController.navigate("search") }, // 11. Navigation
                onProfileClick = {
                    if (isLoggedIn) {
                        navController.navigate("profile") // 11. Navigation zu Profil
                    } else {
                        navController.navigate("login") // 11. Navigation zu Login
                    }
                },
                onMailClick = { navController.navigate("messages") } // 11. Navigation zu Nachrichten
            )
        }
    ) { innerPadding ->
        // 2. LazyColumn für performante Scroll-Liste (80% aller Listen brauchen nur dies)
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // Hellgrauer Hintergrund
        ) {
            // Begrüßungstext (nur bei eingeloggtem Benutzer)
            if (isLoggedIn && !username.isNullOrEmpty()) {
                item {
                    Text(
                        "Willkommen zurück, $username 👋", // 6. Dynamischer Benutzername
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }

            // E-Mail-Button (fester Action-Button)
            item {
                Card( // 5. Klickbare Karte
                    onClick = { navController.navigate("email") }, // 11. Navigation
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp), // Schatten
                    shape = RoundedCornerShape(16.dp), // 3. Abgerundete Ecken
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White // Weißer Hintergrund
                    )
                ) {
                    Row( // 1. Horizontale Anordnung von Elementen
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically // 7. Vertikal zentrieren
                    ) {
                        Icon( // 4. E-Mail-Symbol
                            imageVector = Icons.Default.Email,
                            contentDescription = "E-Mail schreiben",
                            tint = Color(0xFF0061A5), // TK-Blau
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp)) // 1. Abstandhalter
                        Text( // Haupttext
                            "E-Mail an den TK-Service",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF1A237E), // Dunkles Blau
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(Modifier.weight(1f)) // 1. Drückt Pfeil nach rechts
                        Icon( // Pfeil für "Weiter"
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color(0xFF1976D2) // Hellblau
                        )
                    }
                }
            }

            // 2. Dynamische Liste der Nachrichten (80% der Liste mit 20% Code)
            items(messages) { message -> // 2. Iteriert durch alle Nachrichten
                Card(
                    onClick = { navController.navigate("messageDetail/${message.id}") }, // 11. Navigation zu Detailansicht
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column( // 1. Vertikale Anordnung der Nachrichtendetails
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Datum (kleiner, grauer Text)
                        Text(
                            text = message.date,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Titel (fett, dunkelblau)
                        Text(
                            text = message.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A237E)
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Vorschau-Text (dunkelgrau, auf 100 Zeichen begrenzt)
                        Text(
                            text = message.content.take(100) + if (message.content.length > 100) "..." else "",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color(0xFF424242)
                            ),
                            maxLines = 3, // 9. Maximal 3 Zeilen
                            overflow = TextOverflow.Ellipsis, // 9. "..." bei Überlauf
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // "Mehr lesen"-Indikator (Pfeil rechts)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End // 7. Rechtsbündig
                        ) {
                            Text(
                                "", // Leerer Text (könnte "Mehr lesen" sein)
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1976D2)
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Mehr lesen",
                                modifier = Modifier.size(16.dp),
                                tint = Color(0xFF1976D2) // Hellblau
                            )
                        }
                    }
                }
            }
        }
    }
}
