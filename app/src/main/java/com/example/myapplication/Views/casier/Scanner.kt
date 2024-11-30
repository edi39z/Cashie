package com.example.myapplication.views.casier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.example.myapplication.R
import com.example.myapplication.views.casier.`fun`.BarcodeScanner

class Scanner : ComponentActivity() {

    private lateinit var barcodeScanner: BarcodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pastikan BarcodeScanner didefinisikan dengan benar
        barcodeScanner = BarcodeScanner(this)
        setContent {
            ScannerScreen(barcodeScanner)
        }
    }
}

@Composable
fun ScannerScreen(barcodeScanner: BarcodeScanner) {
    var scanResult by remember { mutableStateOf<String?>(null) }
    var isScanning by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Menambahkan background putih
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (!isScanning) {
                    isScanning = true
                    barcodeScanner.startScanInBackground { result ->
                        scanResult = result
                        isScanning = false
                    }
                }
            },
            enabled = true,
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(Color(0xFFFDDE55)),
            modifier = Modifier
                .size(100.dp) // Ukuran tombol
                .padding(16.dp) // Padding
        ) {
            Icon(
                painter = painterResource(id = R.drawable.camera),
                contentDescription = "Camera Icon",
                modifier = Modifier.size(80.dp), // Ukuran ikon
                tint = Color.Black
            )
        }

        // Jika hasil scan ada, tampilkan teks tambahan dengan nilai barcode
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



