package com.example.myapplication.views.database

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.product.Product
import com.example.myapplication.product.ProductViewModel

@Composable
fun DataPage(navController: NavController, productViewModel: ProductViewModel) {
    val products by productViewModel.products.collectAsState()
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<Product?>(null) }

    LaunchedEffect(Unit) {
        productViewModel.listenToProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header tabel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE0F7FA))
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Kodes",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Nama Barang",
                modifier = Modifier.weight(2f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Jumlah",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Harga (Rp)",
                modifier = Modifier.weight(2f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp)) // For edit and delete icons
        }

        // Daftar produk
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                ProductRow(
                    product = product,
                    onEditClick = {
                        navController.navigate("EditProductScreen/${product.id_produk}")

                    },
                    onDeleteClick = {
                        productToDelete = product
                        showDeleteConfirmationDialog = true
                    }
                )
            }
            item {
                Button(
                    onClick = { navController.navigate("AddProductScreen") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26C6DA))
                ) {
                    Text(text = "Tambah Produk", color = Color.White)
                }
            }
        }
        // Tombol "Tambah Produk"
//        Button(
//            onClick = { navController.navigate("AddProductScreen") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 16.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF26C6DA))
//        ) {
//            Text(text = "Tambah Produk", color = Color.White)
//        }
    }

    // Konfirmasi dialog untuk menghapus produk
    if (showDeleteConfirmationDialog && productToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            title = { Text(text = "Hapus Produk") },
            text = {
                Text(text = "Apakah Anda yakin ingin menghapus produk \"${productToDelete?.nama_produk}\"?")
            },
            confirmButton = {
                TextButton(onClick = {
                    productViewModel.deleteProduct(productToDelete!!.id_produk)
                    Log.d("ProductViewModel", "Product deleted: ${productToDelete!!.id_produk}")
                    showDeleteConfirmationDialog = false
                }) {
                    Text(text = "Hapus", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteConfirmationDialog = false
                }) {
                    Text(text = "Batal")
                }
            }
        )
    }
}
@Composable
fun ProductRow(
    product: Product,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD))
            .padding(vertical = 8.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = product.id_produk,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = product.nama_produk,
            modifier = Modifier.weight(2f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = product.stock_produk.toString(),
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Rp ${product.harga_produk}",
            modifier = Modifier.weight(2f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        IconButton(onClick = onEditClick) {
            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
        }
        IconButton(onClick = onDeleteClick) {
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
        }
    }
}