package com.example.myapplication.views.history

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.product.Product
import com.example.myapplication.views.database.ProductRow

@Composable
fun HistoryPage() {
    // Daftar produk yang difilter
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        val filteredProducts = products.filter {
//            it.name.contains(searchQuery, ignoreCase = true)
//        }
//
//        items(filteredProducts) { product ->
//            ProductRow(
//                product = product,
//                onEditClick = {
//                    navController.navigate("EditProductScreen/${product.id}")
//                },
//                onDeleteClick = {
//                    productToDelete = product
//                    showDeleteConfirmationDialog = true
//                }
//            )
//        }
//    }
}