package com.example.tkapp.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onMenuClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onMailClick: () -> Unit = {}
) {
    TopAppBar(
        title = { Text("Willkommen in Ihrer TK-App") },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = onLoginClick) {   // 👉 Navigation Login
                Icon(Icons.Default.Person, contentDescription = "Login")
            }
            IconButton(onClick = onMailClick) {
                Icon(Icons.Default.Email, contentDescription = "Mail")
            }
        }
    )
}

