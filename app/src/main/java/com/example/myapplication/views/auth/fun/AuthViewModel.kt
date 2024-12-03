package com.example.myapplication.views.auth.`fun`;

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

open class AuthViewModel(
    context : Context
) : ViewModel() {

    protected val authManager = AuthManager(context)

    fun signInWithGoogle(
        onSuccess: () -> Unit
    ){
        viewModelScope.launch{
            authManager.signInWithGoogle{
                onSuccess()
            }
        }
    }

}