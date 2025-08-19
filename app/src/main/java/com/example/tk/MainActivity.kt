package com.example.tk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tk.ui.screen.DashboardScreen
import com.example.tk.ui.screen.LoginScreen
import com.example.tk.ui.screen.ReportScreen
import com.example.tk.viewmodel.LoginViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.tk.ui.screen.EmailScreen
import com.example.tk.ui.screen.MessageDetailScreen
import com.example.tk.ui.screen.ProfileScreen
import com.example.tk.ui.screen.SearchScreen
import com.example.tk.viewmodel.ReportViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Setzt den Compose-UI-Baum als Content-View der Activity
        setContent {
            // NavController ist das Herzstück der Navigation - verwaltet den Back-Stack
            val navController = rememberNavController()

            // ViewModel-Instanz, die über die gesamte App lebensfähig bleibt
            val loginViewModel: LoginViewModel = viewModel()

            // NavHost ist der Container für alle navigierbaren Screens
            NavHost(
                navController = navController,
                startDestination = "dashboard"  // Erster Screen beim App-Start
            ) {
                // Dashboard-Screen Definition
                composable("dashboard") {
                    // ViewModel für diesen spezifischen Screen
                    val reportViewModel: ReportViewModel = viewModel()
                    DashboardScreen(
                        navController = navController,
                        loginViewModel = loginViewModel,
                        reportViewModel = reportViewModel
                    )
                }

                // Report-Screen (einfache Version ohne ViewModel)
                composable("report") {
                    ReportScreen(navController)
                }

                // Platzhalter für Messages-Screen
                composable("messages") {
                    // TODO: Implementieren
                }

                // Login-Screen mit ViewModel für Authentifizierungslogik
                composable("login") {
                    LoginScreen(
                        navController = navController,
                        viewModel = loginViewModel
                    )
                }

                // Profil-Screen (einfache Version)
                composable("profile") {
                    ProfileScreen(navController)
                }

                // Such-Screen
                composable("search") {
                    SearchScreen(navController)
                }

                // Dynamischer Screen mit Parameterübergabe
                composable(
                    "messageDetail/{messageId}",  // Route mit Parameter
                    arguments = listOf(
                        // Definition des Parameters mit Typ
                        navArgument("messageId") { type = NavType.IntType }
                    )
                ) { backStackEntry ->
                    // ViewModel für Nachrichten-Details
                    val reportViewModel: ReportViewModel = viewModel()

                    // Parameter aus der Route extrahieren
                    val messageId = backStackEntry.arguments?.getInt("messageId") ?: 0

                    // Nachricht aus dem ViewModel holen
                    val message = reportViewModel.getMessageById(messageId)

                    // Entweder DetailScreen anzeigen oder Fehlermeldung
                    message?.let {
                        MessageDetailScreen(
                            navController = navController,
                            message = it
                        )
                    } ?: run {
                        Text("Nachricht nicht gefunden")
                    }
                }

                // Email-Screen
                composable("email") {
                    EmailScreen(navController)
                }
            }
        }
    }
}