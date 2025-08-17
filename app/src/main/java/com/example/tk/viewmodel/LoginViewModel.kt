package com.example.tk.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel: ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _username = MutableStateFlow<String?>(null)
    val username: StateFlow<String?> = _username


    fun isLoggedIn(): Boolean = _isLoggedIn.value
    // In LoginViewModel.kt
    private val _email = MutableStateFlow<String?>(null)
    val email: StateFlow<String?> = _email

    fun login(email: String, password: String): Boolean {
        return if (email.isNotEmpty() && password.isNotEmpty()) {
            _isLoggedIn.value = true
            _username.value = email.split("@").firstOrNull() ?: "Benutzer"
            _email.value = email
            true
        } else {
            false
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _username.value = null
        _email.value = null
    }
}