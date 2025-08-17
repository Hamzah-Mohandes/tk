package com.example.tk.ui.screen


import android.R.attr.value
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tk.domain.model.Message
import com.example.tk.viewmodel.LoginViewModel
import com.example.tk.viewmodel.ReportViewModel
import com.example.tkapp.ui.components.TopBar


@Composable
fun DashboardScreen(
    navController: NavController,
    loginViewModel: LoginViewModel,
    reportViewModel: ReportViewModel
) {

    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
    val username by loginViewModel.username.collectAsState()
    val messages : List<Message> = reportViewModel.messegae.collectAsState(initial = emptyList()).value

    Scaffold(
        topBar = { TopBar(onLoginClick = { navController.navigate("login") }) }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            if (isLoggedIn) {
                Text("Willkommen zurück, $username 👋", style = MaterialTheme.typography.titleLarge)
            } else {
                Text("Bitte loggen Sie sich ein", style = MaterialTheme.typography.titleLarge)
            }


            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(messages) { message ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(message.title, style = MaterialTheme.typography.titleMedium)
                            Text(message.content, style = MaterialTheme.typography.bodyMedium)
                            Text(message.date, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}
