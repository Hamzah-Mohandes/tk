package com.example.tk.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.* // 1. Grundlegende Layouts (Column, Row, Spacer etc.)
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.* // 2. Material-Design-Komponenten (Button, Card, etc.)
import androidx.compose.runtime.* // 3. State-Management (collectAsState, rememberCoroutineScope)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier // 4. Modifier für Layout-Anpassungen
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController // 5. Navigation
import com.example.tk.viewmodel.ReportViewModel
import kotlinx.coroutines.launch // 6. Asynchrone Operationen (scope.launch)

@OptIn(ExperimentalMaterial3Api::class)
@Composable // 1. Jede UI-Komponente ist eine @Composable-Funktion
fun ReportScreen(
    navController: NavController, // 5. Navigation zwischen Screens
    viewModel: ReportViewModel = viewModel() // 3. ViewModel für State-Management
) {
    // Farbdefinitionen (könnten auch in einer Theme.kt liegen)
    val tkBlue = Color(0xFF0061A5)
    val tkLightBlue = Color(0xFFE3F2FD)

    // 3. State aus dem ViewModel beobachten (reaktive UI)
    val reportText by viewModel.reportText.collectAsState()

    // 6. Coroutine-Scope für asynchrone Aktionen (z. B. API-Calls)
    val scope = rememberCoroutineScope()

    // 7. Scaffold als Grundgerüst für Bildschirme mit TopAppBar, BottomBar etc.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Neue Krankmeldung", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // 5. Navigation: Zurück-Button
                        Icon(Icons.Default.ArrowBack, contentDescription = "Zurück", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = tkBlue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column( // 1. Grundlayout: Spaltenweise Anordnung
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F9FA))
        ) {
            // Header-Karte mit Rundungen und Schatten
            Card( // 2. Material-Komponente für Karten
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Krankmeldung erfassen",
                        style = MaterialTheme.typography.titleLarge, // 9. Typografie aus dem Theme
                        color = tkBlue,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // 1. Abstände mit Spacer
                    Text(
                        "Bitte beschreiben Sie Ihre Symptome...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            // Eingabebereich mit TextField
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .weight(1f), // 4. Modifier: Füllt verfügbaren Platz
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Text(
                        "Beschreibung der Symptome",
                        style = MaterialTheme.typography.titleSmall,
                        color = tkBlue,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // 2. OutlinedTextField für Texteingabe
                    OutlinedTextField(
                        value = reportText, // 3. Gebundener State
                        onValueChange = { viewModel.updateReport(it) }, // 3. State-Update
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        placeholder = { Text("Bitte beschreiben Sie hier Ihre Symptome...") },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = tkBlue,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )
                    // Zeichenzähler-Logik
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (reportText.isNotEmpty() && reportText.length < 20) {
                            Text(
                                "Bitte beschreiben Sie Ihre Symptome genauer",
                                color = MaterialTheme.colorScheme.error // 9. Fehlerfarbe aus Theme
                            )
                        }
                        Text("${reportText.length}/1000")
                    }
                }
            }

            // Aktionsbutton (absenden)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = Color.Transparent,
                shadowElevation = 8.dp
            ) {
                Button( // 2. Material-Button
                    onClick = {
                        scope.launch { // 6. Asynchrone Aktion (z. B. API-Call)
                            // Submit-Logik hier
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = reportText.isNotBlank() && reportText.length in 20..1000, // Button nur aktiv, wenn Bedingungen erfüllt
                    colors = ButtonDefaults.buttonColors(
                        containerColor = tkBlue,
                        disabledContainerColor = Color.Gray.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Krankmeldung absenden",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
