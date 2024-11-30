package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

class DatabaseActivity : ComponentActivity() {

    private lateinit var barcodeScanner: BarcodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi BarcodeScanner
        barcodeScanner = BarcodeScanner(this)
        setContent {
            DatabaseScreen(barcodeScanner)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatabaseScreen(barcodeScanner: BarcodeScanner) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray)
        ) {
            ScannerScreen(barcodeScanner = barcodeScanner)
        }
    }

