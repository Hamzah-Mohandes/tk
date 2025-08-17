package com.example.tk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tk.ui.screen.DashboardScreen
import com.example.tk.ui.screen.LoginScreen
import com.example.tk.ui.screen.ReportScreen
import com.example.tk.viewmodel.LoginViewModel


// Am Anfang der Datei hinzufügen:
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    ReportScreen()
                }
                composable("login") {
                    LoginScreen(navController, loginViewModel)
                }
            }
        }
    }
}