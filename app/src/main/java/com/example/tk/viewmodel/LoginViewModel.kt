package com.example.tk.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {
    // 1. Authentifizierungsstatus - Der wichtigste State im ViewModel
    // Privater, veränderbarer StateFlow
    private val _isLoggedIn = MutableStateFlow(false)
    // Öffentliche, schreibgeschützte Version für die UI
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    // 2. Benutzerdaten - Optionaler String (kann null sein)
    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username

    // 3. Hilfsfunktion - Synchroner Check des Login-Status
    fun isLoggedIn(): Boolean = _isLoggedIn.value

    // 4. E-Mail-State - Für die Anzeige/Verwendung in der UI
    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> = _email

    // 5. Kernfunktion - Login-Logik (Mock-Implementierung)
    fun login(email: String, password: String): Boolean {
        return if (email.isNotEmpty() && password.isNotEmpty()) {
            // 5a. Erfolgsfall:
            _isLoggedIn.value = true
            // Benutzername aus E-Mail extrahieren (alles vor dem @)
            _username.value = email.split("@").firstOrNull() ?: "Benutzer"
            _email.value = email
            true // Rückgabe: Erfolg
        } else {
            // 5b. Fehlerfall:
            false // Rückgabe: Fehler
        }
    }

    // 6. Logout-Funktion - Setzt alle States zurück
    fun logout() {
        _isLoggedIn.value = false
        _username.value = null
        _email.value = null
    }
}