// MessageDetailScreen.kt
package com.example.tk.ui.screen

import android.R.color.white
import androidx.compose.foundation.background
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tk.domain.model.Message
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageDetailScreen(
    navController: NavController,
    message: Message
) {
    val tkBlue = Color(0xFF0061A5)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Nachricht",
                        color = tkBlue
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Zurück",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = tkBlue,
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(tkBlue)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = message.title,
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = message.date,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.White.copy(alpha = 0.7f)),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    lineHeight = 24.sp
                )
            }
        }
    }
}