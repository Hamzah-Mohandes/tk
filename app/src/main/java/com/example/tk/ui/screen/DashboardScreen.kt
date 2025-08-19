package com.example.tk.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
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
import com.example.tk.viewmodel.LoginViewModel
import com.example.tk.viewmodel.ReportViewModel
import com.example.tkapp.ui.components.TopBar

@Composable
fun DashboardScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
    reportViewModel: ReportViewModel = viewModel()
) {
    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
    val username by loginViewModel.username.collectAsState()
    val messages by reportViewModel.messages.collectAsState()

    Scaffold(
        topBar = {
            TopBar(
                isLoggedIn = isLoggedIn,
                username = username,
                onMenuClick = { /* Handle menu click */ },
                onSearchClick = { navController.navigate("search") },
                onProfileClick = {
                    if (isLoggedIn) {
                        navController.navigate("profile")
                    } else {
                        navController.navigate("login")
                    }
                },
                onMailClick = { navController.navigate("messages") }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
        ) {
            if (isLoggedIn && !username.isNullOrEmpty()) {
                item {
                    Text(
                        "Willkommen zurück, $username 👋",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    )
                }
            }

            // E-Mail Button
            item {
                Card(
                    onClick = { navController.navigate("email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "E-Mail schreiben",
                            tint = Color(0xFF0061A5),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            "E-Mail an den TK-Service",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color(0xFF1A237E),
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = Color(0xFF1976D2)
                        )
                    }
                }
            }

            // Nachrichten-Liste
            items(messages) { message ->
                Card(
                    onClick = { navController.navigate("messageDetail/${message.id}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Datum
                        Text(
                            text = message.date,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Titel
                        Text(
                            text = message.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A237E)
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        // Vorschau-Text
                        Text(
                            text = message.content.take(100) + if (message.content.length > 100) "..." else "",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color(0xFF424242)
                            ),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        // "Mehr lesen" Button
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                "",
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
                                tint = Color(0xFF1976D2)
                            )
                        }
                    }
                }
            }
        }
    }
}