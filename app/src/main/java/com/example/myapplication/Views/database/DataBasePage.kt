package com.example.myapplication.views.database

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun DataPage(navController: NavController
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "data", fontSize = 20.sp)
    }
}