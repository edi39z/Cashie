package com.example.myapplication.views.casier

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.views.casier.`fun`.BarcodeScanner
import kotlinx.coroutines.launch

@Composable
fun ScannerScreen(barcodeScanner: BarcodeScanner, navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var scanResult by remember { mutableStateOf<String?>(null) }
    var isScanning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Background putih
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (!isScanning) {
                    isScanning = true
                    coroutineScope.launch {
                        val result = barcodeScanner.startScan()
                        scanResult = result
                        isScanning = false
                    }
                }
            },
            enabled = !isScanning,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color(0xFFFDDE55)),
            modifier = Modifier
                .size(100.dp) // Ukuran tombol
                .padding(16.dp) // Padding untuk tombol
        ) {
            Icon(
                painter = painterResource(id = R.drawable.casier),
                contentDescription = "Camera Icon",
                modifier = Modifier
                    .size(88.dp), // Ukuran ikon hampir sebesar tombol (100.dp - sedikit margin)
                tint = Color.Black
            )
        }

        if (scanResult != null) {
            Text(
                text = "Hasil Barcode: $scanResult",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (isScanning) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }
}
