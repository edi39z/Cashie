package com.example.myapplication.views.database

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun PinScreen(
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Pin", fontSize = 20.sp)
    }
}