package com.example.myapplication.views.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ProfilePageWithViewModel(viewModel: ProfileViewModel = viewModel()) {
    val userState = viewModel.user.collectAsState()

    val user = userState.value
    if (user != null) {
        ProfilePage(user = user)
    } else {
        // Loading Indicator
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
