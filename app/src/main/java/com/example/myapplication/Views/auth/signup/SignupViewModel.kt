package com.example.myapplication.views.auth.signup

import android.content.Context
import com.example.myapplication.views.auth.`fun`.AuthViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel(
    context: Context
): AuthViewModel(context) {

    // State flows for UI states
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _isPasswordVisible = MutableStateFlow(false)
    val isPasswordVisible = _isPasswordVisible.asStateFlow()

    private val _confirmPassword = MutableStateFlow("")
    val confirmPassword = _confirmPassword.asStateFlow()

    private val _isConfirmPasswordVisible = MutableStateFlow(false)
    val isConfirmPasswordVisible = _isConfirmPasswordVisible.asStateFlow()

    fun updateConfirmPassword(newPassword: String) {
        _confirmPassword.value = newPassword.replace(" ", "")
    }

    fun toggleConfirmPasswordVisibility() {
        _isConfirmPasswordVisible.value = !_isConfirmPasswordVisible.value
    }

    fun updateEmail(newEmail: String) {
        _email.value = newEmail.replace(" ", "")
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword.replace(" ", "")
    }

    fun togglePasswordVisibility() {
        _isPasswordVisible.value = !_isPasswordVisible.value
    }

    fun signUpWithEmailAndPassword(
        onLoginSuccess: () -> Unit,
    ) {
        authManager.signUpWithEmailAndPassword(
            _email.value,
            _password.value,
            _confirmPassword.value
        ) {
            onLoginSuccess()
        }
    }
}
