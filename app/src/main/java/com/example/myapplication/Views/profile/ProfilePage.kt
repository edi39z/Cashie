package com.example.myapplication.views.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun ProfilePage(
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Profiel", fontSize = 20.sp)
    }
}