package com.example.myapplication.views.casier

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navbar.BottomBarScreen
import com.example.myapplication.product.PreviewProduct
import com.example.myapplication.product.Product
import com.example.myapplication.product.Receipt
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Gray
import com.example.myapplication.ui.theme.Yellow
import com.example.myapplication.views.casier.`fun`.BarcodeScanner
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID


@Composable
fun CashierPage(barcodeScanner: BarcodeScanner,navController: NavController) {

    val db = Firebase.firestore
    val items by remember { mutableStateOf(mutableMapOf<String, Product>()) }
    val previewMap = remember { mutableStateMapOf<String, PreviewProduct>() }
    var kodeBarang by remember { mutableStateOf("") }
    var showPaymentDialog by remember { mutableStateOf(false) }
    var paymentAmount by remember { mutableStateOf("") }
    var isScanning by remember { mutableStateOf(false) }
//    var isCheck by remember { mutableStateOf(false) }
    var scanResult by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val userDoc = db
        .collection("users")
        .document(Firebase.auth.currentUser!!.uid)

    val itemsCollection = userDoc
        .collection("products")
    var totalPrice = 0.0
    val payment by remember { mutableStateOf("") }
    val context = LocalContext.current


    // Menggunakan LaunchedEffect untuk memuat data
    LaunchedEffect(Unit) {
        try {
            val result = itemsCollection.get().await() // Menggunakan .await() langsung
            for (document in result.documents) {
                val item = document.toObject(Product::class.java)
                if (item != null) {
                    items[item.barcode] = item
                }
            }
            Log.d("kasiee", "Items berhasil dimuat: $items")
        } catch (e: Exception) {
            Log.e("kasiee", "Gagal memuat data: ${e.message}", e)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.cashie),
            contentDescription = null,
            modifier = Modifier
                .width(70.dp)

        )
        Spacer(modifier = Modifier.size(30.dp))
        TextField(
            value = kodeBarang,
            onValueChange = { input ->
                kodeBarang = input // Update kodeBarang dengan nilai baru dari pengguna
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        val foundItem = items[kodeBarang]
                        if (foundItem != null) {
                            val checkPreviewMap = previewMap[foundItem.id]
                            if (checkPreviewMap != null) {
                                val count = checkPreviewMap.count + 1
                                val price = count * foundItem.price
                                previewMap[foundItem.id] = checkPreviewMap.copy(
                                    count = checkPreviewMap.count + 1,
                                    price = price
                                )
                            } else {
                                previewMap[foundItem.id] = PreviewProduct(
                                    productId = foundItem.id,
                                    name = foundItem.name,
                                    barcode = foundItem.barcode,
                                    count = 1,
                                    price = foundItem.price
                                )
                                Log.d("ScanResult", previewMap.toString())
                            }
                        }
                    }
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.Send,
                        null
                    )
                }
            },
            label = { Text("Masukkan manual kode") },
            modifier = Modifier
                .fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.drawable.line),
            contentDescription = "or",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 18.dp)
        )

        Button(
            contentPadding = PaddingValues(0.dp),
            onClick = {
                if (!isScanning) {
                    isScanning = true
                    coroutineScope.launch {
                        val result = barcodeScanner.startScan()
                        scanResult = result
                        isScanning = false

                        // Tambahkan hasil scan ke previewList
                        if (scanResult != null) {
                            val foundItem = items[scanResult]
                            if (foundItem != null) {
                                val checkPreviewMap = previewMap[foundItem.id]
                                if (checkPreviewMap != null) {
                                    val count = checkPreviewMap.count + 1
                                    val price = count * foundItem.price
                                    previewMap[foundItem.id] = checkPreviewMap.copy(
                                        count = checkPreviewMap.count + 1,
                                        price = price
                                    )
                                } else {
                                    previewMap[foundItem.id] = PreviewProduct(
                                        productId = foundItem.id,
                                        name = foundItem.name,
                                        barcode = foundItem.barcode,
                                        count = 1,
                                        price = foundItem.price
                                    )
                                    Log.d("ScanResult", previewMap.toString())
                                }
                            }
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(Yellow),
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .clip(RoundedCornerShape(0.dp))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Scan Barcode",
                    color = Color.Black,
                    fontSize = 16.sp
                )
                Spacer(Modifier.size(15.dp))
                Icon(
                    painter = painterResource(id = R.drawable.casier),
                    contentDescription = "Scanner",
                    modifier = Modifier
                        .size(35.dp),
                    tint = Color.Black
                )
            }
        }

        Spacer(Modifier.size(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = {
//                            if (isCheck == false){
//                                if (lastIndex >= 0) {
//                                    val lastItem = previewList[lastIndex]
//                                    previewList[lastIndex] = lastItem.copy(jumlah = lastItem.jumlah + 1)
//                                    Log.d("Kasir2", previewList.toString())
//                                    isCheck = true
//                                }
//                            }
                    },
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                ) {
                    Text(
                        "x2",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                ) {
                    Text(
                        "x3",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                ) {
                    Text(
                        "x4",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }



                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                ) {
                    Text(
                        "x5",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                ) {
                    Text(
                        "x10",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                ) {
                    Text(
                        "x50",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }

            }

            Spacer(modifier = Modifier.size(20.dp))


            Column(
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Text(
                    "Preview",
                    fontSize = 17.sp,
                    fontWeight = FontWeight(700)
                )
                Column(
                    modifier = Modifier
                        .background(
                            color = Blue,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 14.dp)
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                    ) {
                        // Add your table here
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Nama",
                                fontSize = 13.sp,
                                fontWeight = FontWeight(700)
                            )
                            Text(
                                "Jumlah",
                                fontSize = 13.sp,
                                fontWeight = FontWeight(700)
                            )
                            Text(
                                "Harga",
                                fontSize = 13.sp,
                                fontWeight = FontWeight(700)
                            )
                        }
                        Spacer(Modifier.size(8.dp))

                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState())
                        ) {
                            previewMap.values.forEach { map ->
                                totalPrice += map.price
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = map.name, // Default to "Unknown" if null
                                        fontSize = 11.sp,
                                    )
                                    Text(
                                        text = map.count.toString(), // Convert Int to String
                                        fontSize = 11.sp
                                    )
                                    Text(
                                        text = map.price.toString(), // Convert Int to String
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }


                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier

                            .fillMaxSize()
                            .height(50.dp)

                    ) {
                        Text(
                            "Total: $totalPrice",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(700)
                        )
                        Spacer(Modifier.size(10.dp))


                        Button(
                            onClick = { showPaymentDialog = true },
                            colors = ButtonDefaults.buttonColors(Yellow),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .width(70.dp)
                                .height(25.dp)
                                .clip(RoundedCornerShape(10.dp))
                        ) {
                            Text(
                                "Next",
                                color = Color.Black,
                                fontSize = 13.sp
                            )
                        }


                    }
                    if (showPaymentDialog) {
                        AlertDialog(
                            onDismissRequest = { showPaymentDialog = false },
                            title = {
                                Text("Pembayaran", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            },
                            text = {
                                Column {
                                    Text("Total harga: Rp$totalPrice")
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedTextField(
                                        value = paymentAmount,
                                        onValueChange = { paymentAmount = it },
                                        label = { Text("Jumlah Bayar") },
                                        modifier = Modifier.fillMaxWidth(),
                                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                                    )
                                }
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        val payment = paymentAmount.toDoubleOrNull()
                                        if (payment != null) {
                                            if (payment >= totalPrice.toDouble()) {
                                                coroutineScope.launch {
                                                    try {
                                                        // Update stok di Firestore
                                                        previewMap.values.forEach { itemPreview ->
                                                            val kodeProduk = itemPreview.productId
                                                            val count = itemPreview.count
                                                            val itemRef = itemsCollection.document(kodeProduk)
                                                            itemRef.update(
                                                                "stock",
                                                                FieldValue.increment(-count.toLong())
                                                            ).await()
                                                        }

                                                        // Hitung kembalian
                                                        val changes = payment - totalPrice

                                                        val receiptDoc = userDoc.collection("receipts").document()
                                                        val receiptId = receiptDoc.id
                                                        receiptDoc
                                                            .set(
                                                                Receipt(
                                                                    id = receiptId,
                                                                    items = previewMap.values.toList(),
                                                                    totalPrice = totalPrice,
                                                                    changes = changes,
                                                                    payment = payment,
                                                                    timestamp = Timestamp.now()
                                                                )
                                                            )
                                                            .await()

                                                        // Tampilkan pesan sukses
                                                        Toast.makeText(
                                                            context,
                                                            "Pembayaran berhasil!",
                                                            Toast.LENGTH_SHORT
                                                        ).show()

                                                        // Navigasi ke HistoryPage
                                                        navController.navigate(BottomBarScreen.History.route)
                                                        showPaymentDialog = false // Tutup dialog
                                                    } catch (e: Exception) {
                                                        Log.e("PaymentError", "Gagal melakukan pembayaran", e)
                                                        Toast.makeText(
                                                            context,
                                                            "Terjadi kesalahan saat pembayaran",
                                                            Toast.LENGTH_SHORT
                                                        ).show()
                                                    }
                                                }
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Pembayaran tidak cukup!",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Masukkan jumlah yang valid!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(Yellow)
                                ) {
                                    Text("OK", color = Color.Black)
                                }

                            },
                            dismissButton = {
                                Button(
                                    onClick = { showPaymentDialog = false },
                                    colors = ButtonDefaults.buttonColors(Color.Gray)
                                ) {
                                    Text("Batal", color = Color.Black)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
