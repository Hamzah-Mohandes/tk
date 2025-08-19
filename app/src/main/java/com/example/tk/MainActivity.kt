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
        setContent {
            val navController = rememberNavController()
            val loginViewModel: LoginViewModel = viewModel()

            NavHost(navController = navController, startDestination = "dashboard") {
                composable("dashboard") {
                    val reportViewModel: ReportViewModel = viewModel()
                    DashboardScreen(navController, loginViewModel, reportViewModel)
                }
                composable("report") {
                    ReportScreen( navController)
                }
                composable("messages") {
                }
                composable("login") {
                    LoginScreen(navController, loginViewModel)
                }
                composable("profile") {
                    // Profil-Screen
                    ProfileScreen(navController)
                }
                composable("search") {
                    // Such-Screen
                    SearchScreen(navController)
                }
                // In MainActivity.kt
                composable(
                    "messageDetail/{messageId}",
                    arguments = listOf(navArgument("messageId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val reportViewModel: ReportViewModel = viewModel()
                    val messageId = backStackEntry.arguments?.getInt("messageId") ?: 0
                    val message = reportViewModel.getMessageById(messageId)

                    message?.let {
                        MessageDetailScreen(
                            navController = navController,
                            message = it
                        )
                    } ?: run {
                        Text("Nachricht nicht gefunden")
                    }
                }
                composable("email") {
                    // Email-Screen
                    EmailScreen(navController)
                }
            }
        }
    }
}