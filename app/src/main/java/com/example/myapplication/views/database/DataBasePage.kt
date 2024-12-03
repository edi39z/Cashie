package com.example.myapplication.views.database

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.product.Product
import com.example.myapplication.product.ProductViewModel

@Composable
fun DataPage(navController: NavController, productViewModel: ProductViewModel) {
    val products by productViewModel.products.collectAsState()
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var productToDelete by remember { mutableStateOf<Product?>(null) }
    var searchQuery by remember { mutableStateOf("") } // State untuk menyimpan input pencarian

    LaunchedEffect(Unit) {
        productViewModel.listenToProducts()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            label = { Text("Cari produk",) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                )
            },
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors().copy(
                focusedIndicatorColor = Color.Black,
                focusedLeadingIconColor = Color.Black,
                focusedLabelColor = Color.Black

            )
        )




        // Daftar produk yang difilter
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val filteredProducts = products.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }

            items(filteredProducts) { product ->
                ProductRow(
                    product = product,
                    onEditClick = {
                        navController.navigate("EditProductScreen/${product.id}")
                    },
                    onDeleteClick = {
                        productToDelete = product
                        showDeleteConfirmationDialog = true
                    }
                )
            }

            // Tombol tambah produk
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
    }

    // Konfirmasi dialog untuk menghapus produk
    if (showDeleteConfirmationDialog && productToDelete != null) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirmationDialog = false },
            title = { Text(text = "Hapus Produk") },
            text = {
                Text(text = "Apakah Anda yakin ingin menghapus produk \"${productToDelete?.name}\"?")
            },
            confirmButton = {
                TextButton(onClick = {
                    productViewModel.deleteProduct(productToDelete!!.id)
                    Log.d("ProductViewModel", "Product deleted: ${productToDelete!!.barcode}")
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Kolom untuk Nama, Stock, dan Harga di sebelah kiri
            Column(
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    text = product.name,
                    fontSize = 18.sp, // Font lebih besar untuk nama produk
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp)) // Spasi antara elemen
                Text(
                    text = "Stock: ${product.stock}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(4.dp)) // Spasi antara elemen
                Text(
                    text = "Harga: Rp ${product.price}",
                    fontSize = 14.sp,
                    color = Color.DarkGray
                )
            }

            // Kolom untuk tombol Edit dan Delete di sebelah kanan
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = onEditClick) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}


