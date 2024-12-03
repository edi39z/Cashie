package com.example.myapplication.views.history


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.product.PreviewProduct
import com.example.myapplication.product.Receipt
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await
import java.text.NumberFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun HistoryPage() {
    val db = Firebase.firestore
    val currentUser = Firebase.auth.currentUser
    val receiptsCollection = db
        .collection("users")
        .document(currentUser!!.uid)
        .collection("receipts")

    var receipts by remember { mutableStateOf<List<Receipt>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Load receipts from Firestore
    LaunchedEffect(Unit) {
        try {
            Log.d("HistoryPage", "Fetching receipts for user: ${currentUser.uid}")
            val result = receiptsCollection.get().await()
            Log.d("HistoryPage", "Fetched documents: ${result.documents}")

            receipts = result.documents.mapNotNull { doc ->
                try {
                    val id = doc.getString("id") ?: ""
                    val items = doc.get("items") as List<Map<String, Any>>? ?: emptyList()
                    val totalPrice = doc.getDouble("totalPrice") ?: 0.0
                    val changes = doc.getDouble("changes") ?: 0.0
                    val payment = doc.getDouble("payment") ?: 0.0
                    val timestamp = doc.getTimestamp("timestamp") ?: Timestamp.now()

                    Receipt(
                        id = id,
                        items = items.map { item ->
                            PreviewProduct(
                                productId = item["productId"] as String,
                                barcode = item["barcode"] as String,
                                name = item["name"] as String,
                                count = (item["count"] as Long).toInt(),
                                price = (item["price"] as Number).toDouble()
                            )
                        },
                        totalPrice = totalPrice,
                        changes = changes,
                        payment = payment,
                        timestamp = timestamp
                    )
                } catch (e: Exception) {
                    Log.e("HistoryPage", "Error parsing receipt: ${e.message}")
                    null
                }
            }

            isLoading = false
        } catch (e: Exception) {
            Log.e("HistoryPage", "Error fetching receipts: ${e.message}")
            isLoading = false
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "History", fontSize = 24.sp, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            receipts.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "No transactions found", fontSize = 16.sp)
                }
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(receipts) { receipt ->
                        ReceiptCard(receipt)
                    }
                }
            }
        }
    }
}

@Composable
fun ReceiptCard(receipt: Receipt) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Logo dan Tanggal
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Logo (ubah dengan logo Cashie)
                Image(
                    painter = painterResource(id = R.drawable.cashie), // Ganti dengan ID logo Cashie
                    contentDescription = "Cashie Logo",
                    modifier = Modifier.size(40.dp)
                )
                // Tanggal
                val formattedDate = DateTimeFormatter
                    .ofPattern("dd MMM yyyy\nHH:mm:ss")
                    .withZone(ZoneId.systemDefault())
                    .format(Instant.ofEpochSecond(receipt.timestamp.seconds))
                Text(
                    textAlign = TextAlign.End,
                    text = formattedDate,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Table Header (Description and Subtotal)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "DESCRIPTION",
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "SUBTOTAL",
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Items List
            receipt.items.forEach { item ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Deskripsi item
                    Text(
                        text = "${item.name.uppercase()} x${item.count}",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                    // Subtotal per item
                    Text(
                        text = "Rp${formatCurrency(item.count * item.price)}",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            // Total Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Rp${formatCurrency(receipt.totalPrice)}",
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
fun formatCurrency(amount: Double): String {
    val formatter = NumberFormat.getNumberInstance(Locale("in", "ID")) // Locale Indonesia
    return formatter.format(amount.toInt())
}

