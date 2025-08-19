package com.example.tk.ui.screen

import android.content.Intent // 1. WICHTIG: Für E-Mail-Intent
import android.net.Uri // 2. WICHTIG: Für Datei-URIs (Bilder, Mailto-Links)
import androidx.activity.compose.rememberLauncherForActivityResult // 3. WICHTIG: Bildauswahl
import androidx.activity.result.contract.ActivityResultContracts.GetContent // 3. Standardvertrag für Dateiauswahl
import androidx.compose.foundation.layout.* // 4. WICHTIG: Layout-Komponenten
import androidx.compose.foundation.text.KeyboardOptions // 5. Tastatur-Einstellungen
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.* // 6. WICHTIG: UI-Komponenten
import androidx.compose.runtime.* // 7. WICHTIG: State-Management
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext // 8. WICHTIG: Zugriff auf Android-Kontext
import androidx.compose.ui.text.input.ImeAction // 5. Tastatur-Aktionen (Done-Button)
import androidx.compose.ui.text.input.KeyboardType // 5. Tastatur-Typ (Text, E-Mail etc.)
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.Image // 9. Bildanzeige
import coil.compose.rememberImagePainter // 10. Bildlader

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailScreen(navController: NavController) {
    // 8. Kontext für Intents und Ressourcen
    val context = LocalContext.current

    // 7. State für Formularfelder und Bild
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // App-spezifische Farbe
    val tkBlue = Color(0xFF0061A5)

    // 3. Launcher für Bildauswahl (20% Code, 80% Funktionalität)
    val pickImageLauncher = rememberLauncherForActivityResult(GetContent()) { uri ->
        photoUri = uri // Aktualisiert State bei Bildauswahl
    }

    // 11. E-Mail-Versand-Logik (20% Code, 80% der Aktion)
    fun sendEmail() {
        Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:service@tk.de") // 2. Mailto-URI
            putExtra(Intent.EXTRA_SUBJECT, subject) // Betreff
            putExtra(Intent.EXTRA_TEXT, message)    // Nachrichtentext
            photoUri?.let { uri ->                   // 2. Optional: Anhänge
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION) // 12. WICHTIG: Berechtigung für Anhänge
            }
            // 8. Zeigt E-Mail-Client-Auswahl an
            context.startActivity(Intent.createChooser(this, "E-Mail senden mit"))
        }
    }

    // 6. Scaffold als Grundgerüst
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("E-Mail an TK") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück"
                        , tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = tkBlue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp) // 4. Gleichmäßige Abstände
        ) {
            // Betreff-Feld (80% aller E-Mail-Formulare brauchen nur dies)
            OutlinedTextField(
                value = subject,
                onValueChange = { subject = it },
                label = { Text("Betreff") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tkBlue,
                    focusedLabelColor = tkBlue,
                    cursorColor = tkBlue
                )
            )

            // Nachricht-Feld (mit optimierter Tastatur)
            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Ihre Nachricht") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), // 4. Füllt verfügbaren Platz
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tkBlue,
                    focusedLabelColor = tkBlue,
                    cursorColor = tkBlue
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text, // 5. Standard-Tastatur
                    imeAction = ImeAction.Done        // 5. "Fertig"-Button
                )
            )

            // Bedingte Anzeige: Bild ODER Auswähl-Button
            if (photoUri != null) {
                // 9. & 10. Zeigt ausgewähltes Bild an
                Image(
                    painter = rememberImagePainter(photoUri),
                    contentDescription = "Photo",
                    modifier = Modifier.size(200.dp)
                )
            } else {
                // Button für Bildauswahl
                Button(
                    onClick = { pickImageLauncher.launch("image/*") }, // 3. Startet Bildauswahl
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tkBlue,
                        disabledContainerColor = tkBlue.copy(alpha = 0.5f)
                    )
                ) {
                    Text("Foto auswählen", color = Color.White)
                }
            }

            // Sende-Button (kritische Aktion)
            Button(
                onClick = { sendEmail() }, // 11. Ruft E-Mail-Funktion auf
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                // 7. Nur aktiv, wenn Felder ausgefüllt
                enabled = subject.isNotBlank() && message.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = tkBlue,
                    disabledContainerColor = tkBlue.copy(alpha = 0.5f)
                )
            ) {
                Text("E-Mail senden", color = Color.White)
            }
        }
    }
}
