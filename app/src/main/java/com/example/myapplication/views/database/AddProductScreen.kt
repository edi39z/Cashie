package com.example.myapplication.views.database

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication.product.Product
import com.example.myapplication.views.casier.`fun`.BarcodeScanner
import kotlinx.coroutines.launch

@Composable
fun AddProductPage(
    onProductAdded: (Product) -> Unit,
    barcodeScanner: BarcodeScanner
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productStock by remember { mutableStateOf("") }
    var idBarcode by remember { mutableStateOf("") }
    var isScanning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Text(
                text = "Add Product",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold,
            )
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // TextField untuk Kode Produk
                OutlinedTextField(
                    value = idBarcode,
                    onValueChange = { idBarcode = it }, // Memungkinkan input manual
                    label = { Text("Kode Produk") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (!isScanning) {
                                    isScanning = true
                                    coroutineScope.launch {
                                        val scanResult = barcodeScanner.startScan()
                                        if (scanResult != null) {
                                            idBarcode = scanResult // Mengisi hasil pemindaian
                                        }
                                        isScanning = false
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Scan Barcode"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // TextField untuk Nama Produk
                OutlinedTextField(
                    value = productName,
                    onValueChange = { productName = it },
                    label = { Text("Nama Produk") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // TextField untuk Harga Produk
                OutlinedTextField(
                    value = productPrice,
                    onValueChange = { productPrice = it },
                    label = { Text("Harga Produk") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // TextField untuk Stok Produk
                OutlinedTextField(
                    value = productStock,
                    onValueChange = { productStock = it },
                    label = { Text("Stok Produk") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Tombol untuk menyimpan produk
                Button(
                    onClick = {
                        if (productName.isBlank() || productPrice.toDoubleOrNull() == null || productStock.toIntOrNull() == null) {
                            // Validasi input (tambahkan logika sesuai kebutuhan)
                            return@Button
                        }
                        val product = Product(
                            barcode = idBarcode,
                            name = productName,
                            price = productPrice.toDouble(),
                            stock = productStock.toInt()
                        )
                        onProductAdded(product)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03AED2)),
                ) {
                    Text(text = "Simpan Produk", color = Color.White)
                }

                // Indikator pemindaian barcode
                if (isScanning) {
                    Spacer(modifier = Modifier.height(16.dp))
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
                }
            }
        }
    }
}

