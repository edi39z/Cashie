package com.example.myapplication.views.database

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.product.ProductViewModel

@Composable
fun EditProductPage(
    navController: NavController,
    productViewModel: ProductViewModel,
    userId: String,
    productId: String
) {
    // Menggunakan collectAsState untuk mengawasi perubahan
    val products by productViewModel.products.collectAsState(initial = emptyList())
    val product = products.find { it.id_produk == productId }


    if (product != null) {
        var namaProduk by remember { mutableStateOf(product.nama_produk) }
        var hargaProduk by remember { mutableStateOf(product.harga_produk.toString()) }
        var stokProduk by remember { mutableStateOf(product.stock_produk.toString()) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = "Edit Produk", style = MaterialTheme.typography.titleLarge)

            // Input Nama Produk
            TextField(
                value = namaProduk,
                onValueChange = { namaProduk = it },
                label = { Text("Nama Produk") }
            )

            // Input Harga Produk
            TextField(
                value = hargaProduk,
                onValueChange = { hargaProduk = it },
                label = { Text("Harga Produk") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            // Input Stok Produk
            TextField(
                value = stokProduk,
                onValueChange = { stokProduk = it },
                label = { Text("Stok Produk") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            // Tombol Simpan Perubahan
            Button(
                onClick = {
                    val updatedProduct = product.copy(
                        nama_produk = namaProduk,
                        harga_produk = hargaProduk.toIntOrNull() ?: 0,
                        stock_produk = stokProduk.toIntOrNull() ?: 0
                    )
                    productViewModel.updateProduct(userId, updatedProduct) // Sertakan userId
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Simpan Perubahan")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Produk tidak ditemukan",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
