package com.example.myapplication.views.database

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.myapplication.product.Product
import java.util.UUID

@Composable
fun AddProductPage(
    onProductAdded: (Product) -> Unit
) {
    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productStock by remember { mutableStateOf("") }
    var idProduct  by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = idProduct,
            onValueChange = { idProduct = it },
            label = { Text("Kode Produk") }
        )
        TextField(
            value = productName,
            onValueChange = { productName = it },
            label = { Text("Nama Produk") }
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = productPrice,
            onValueChange = { productPrice = it },
            label = { Text("Harga Produk") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = productStock,
            onValueChange = { productStock = it },
            label = { Text("Stok Produk") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (productName.isBlank() || productPrice.toIntOrNull() == null || productStock.toIntOrNull() == null) {
                    // Tampilkan pesan error
                    return@Button
                }
                val product = Product(
                    id_produk = idProduct,
                    nama_produk = productName,
                    harga_produk = productPrice.toInt(),
                    stock_produk = productStock.toInt()
                )
                onProductAdded(product)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan Produk")
        }

    }
}
