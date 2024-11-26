package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen()
        }
    }
}

@Composable
fun ProfileScreen() {
    Text("Ini adalah halaman Profil!")
}


@Preview(showBackground = true) // Menampilkan background default di preview
@Composable
fun PreviewHomeScreen() {
    ProfileScreen() // Panggil fungsi yang ingin Anda pratinjau
}
