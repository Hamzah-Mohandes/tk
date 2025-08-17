package com.example.tk.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                .padding(16.dp)
        ) {
            if (isLoggedIn && !username.isNullOrEmpty()) {
                item {
                    Text(
                        "Willkommen zurück, $username 👋",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            items(messages) { message ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = message.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = message.content)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = message.date,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}