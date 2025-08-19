package com.example.tk.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tk.domain.model.Message
import com.example.tk.viewmodel.ReportViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: ReportViewModel = viewModel() // 1. ViewModel-Injection
) {
    // 2. Design-Farben
    val tkBlue = Color(0xFF0061A5)

    // 3. State-Management für Suchanfrage
    var searchQuery by remember { mutableStateOf("") }

    // 4. Nachrichten-Stream aus dem ViewModel
    val messages by viewModel.messages.collectAsState()

    // 5. Filterlogik (UI-seitig)
    val filteredMessages = messages.filter { message ->
        message.content.contains(searchQuery, ignoreCase = true) ||
                message.title.contains(searchQuery, ignoreCase = true)
    }

    // 6. Grundgerüst des Screens
    Scaffold(
        topBar = {
            TopAppBar( // 7. AppBar mit Navigation
                title = { Text("Nachrichten durchsuchen", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Zurück", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = tkBlue, // Markenfarbe
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // 8. Suchfeld
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it }, // Update bei Eingabe
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Nachrichten durchsuchen...") },
                leadingIcon = { Icon(Icons.Default.Search, "Suchen", tint = tkBlue) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = tkBlue, // Markenfarbe für Fokus
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(12.dp) // Abgerundete Ecken
            )

            // 9. Dynamische Inhalte basierend auf Suche
            when {
                searchQuery.isBlank() ->
                    PlaceholderText("Geben Sie einen Suchbegriff ein")

                filteredMessages.isEmpty() ->
                    PlaceholderText("Keine Ergebnisse gefunden")

                else -> {
                    SearchResultsCount(filteredMessages.size)
                    MessagesList(filteredMessages, navController)
                }
            }
        }
    }
}

// 10. Wiederverwendbare Komponente für Nachrichteneinträge
@Composable
private fun MessageItem(
    message: Message,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = message.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = message.content,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray
            )
            Text(
                text = message.date,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )
        }
    }
}

// Hilfskomponenten für bessere Lesbarkeit
@Composable
private fun PlaceholderText(text: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, style = MaterialTheme.typography.bodyLarge, color = Color.Gray)
    }
}

@Composable
private fun SearchResultsCount(count: Int) {
    Text(
        "$count Ergebnisse gefunden",
        style = MaterialTheme.typography.bodySmall,
        color = Color.Gray,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@Composable
private fun MessagesList(messages: List<Message>, navController: NavController) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(messages) { message ->
            MessageItem(
                message = message,
                onClick = { navController.navigate("messageDetail/${message.id}") }
            )
        }
    }
}