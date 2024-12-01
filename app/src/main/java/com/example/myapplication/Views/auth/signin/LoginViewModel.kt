package com.example.myapplication.views.auth.signin

import android.content.Context
import com.example.myapplication.views.auth.`fun`.AuthViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel(
    context: Context
) : AuthViewModel(context) {


    // State flows for UI states
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail.replace(" ", "")
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword.replace(" ", "")
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun loginWithEmailAndPassword(
        onLoginSuccess: () -> Unit,
    ) {
        authManager.loginWithEmailAndPassword(_email.value, _password.value) {
            onLoginSuccess()
        }
    }
}