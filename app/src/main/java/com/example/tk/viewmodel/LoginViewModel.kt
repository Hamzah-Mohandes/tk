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

    fun login(user: String, password: String): Boolean {
        return if (user.isNotEmpty() && password.isNotEmpty()) {
            _isLoggedIn.value = true
            _username.value = user
            true
        } else {
            _isLoggedIn.value = false
            _username.value = null
            false
        }
    }

    fun logout() {
        _isLoggedIn.value = false
        _username.value = null
    }
}