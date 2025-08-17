package com.example.tkapp.ui.components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onMenuClick: () -> Unit = {},
    onSearchClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onMailClick: () -> Unit = {}
) {
    // TK-Farben
    val tkBlue = Color(0xFF0061A5)  // TK-Hauptfarbe Blau
    val tkLightBlue = Color(0xFFE5F1F9)  // Helleres Blau für Akzente

    Surface(
        shadowElevation = 4.dp,
        color = tkBlue
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "TK-App",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = onMenuClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        disabledContentColor = Color.Gray
                    )
                ) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = "Menü",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = onSearchClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Suche",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = onLoginClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Login",
                        tint = Color.White
                    )
                }
                IconButton(
                    onClick = onMailClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Nachrichten",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = tkBlue,
                titleContentColor = Color.White,
                actionIconContentColor = Color.White
            )
        )
    }
}