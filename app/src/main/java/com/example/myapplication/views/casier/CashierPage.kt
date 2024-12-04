package com.example.myapplication.views.casier

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.product.PreviewProduct
import com.example.myapplication.product.Product
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Digital
import com.example.myapplication.ui.theme.Gray
import com.example.myapplication.ui.theme.Logo
import com.example.myapplication.ui.theme.Yellow
import com.example.myapplication.views.casier.`fun`.BarcodeScanner
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun CashierPage(barcodeScanner: BarcodeScanner, navController: NavController) {

    val db = Firebase.firestore
    val items by remember { mutableStateOf(mutableMapOf<String, Product>()) }
    val previewMap = remember { mutableStateMapOf<String, PreviewProduct>() }
    var kodeBarang by remember { mutableStateOf("") }
    var isScanning by remember { mutableStateOf(false) }
    var isCheck by remember { mutableStateOf(false) }
    var scanResult by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val itemsCollection = db.collection("users")
        .document(Firebase.auth.currentUser!!.uid)
        .collection("products")
    var totalPrice = 0.0
    val lastScannedProduct = previewMap.entries.lastOrNull()?.value


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
            null
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
                                isCheck = false
                            } else {
                                previewMap[foundItem.id] = PreviewProduct(
                                    productId = foundItem.id,
                                    name = foundItem.name,
                                    barcode = foundItem.barcode,
                                    count = 1,
                                    price = foundItem.price
                                )
                                isCheck = false
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
            ){
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
                        if (isCheck == false){
                            val foundItem = previewMap.values.firstOrNull()  // Assuming you want to update the first item in the previewMap for simplicity
                            if (foundItem != null) {
                                val updatedCount = foundItem.count + 1
                                val updatedPrice = updatedCount * foundItem.price  // Update the price accordingly

                                // Update the previewMap with the new count and price
                                previewMap[foundItem.productId] = foundItem.copy(
                                    count = updatedCount,
                                    price = updatedPrice
                                )

                                // Optionally, log the changes for debugging
                                Log.d("UpdateItem", "Updated ${foundItem.name} count to $updatedCount and price to $updatedPrice")

                                isCheck = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                        .let {
                            if (!isCheck && previewMap.isNotEmpty()) {
                                it.border(2.dp, Yellow, CircleShape)
                            } else {
                                it // return the original modifier if condition not met
                            }
                        }
                ) {
                    Text(
                        "x2",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {
                        if (isCheck == false){
                            val foundItem = previewMap.values.firstOrNull()  // Assuming you want to update the first item in the previewMap for simplicity
                            if (foundItem != null) {
                                val updatedCount = foundItem.count + 2
                                val updatedPrice = updatedCount * foundItem.price  // Update the price accordingly

                                // Update the previewMap with the new count and price
                                previewMap[foundItem.productId] = foundItem.copy(
                                    count = updatedCount,
                                    price = updatedPrice
                                )

                                // Optionally, log the changes for debugging
                                Log.d("UpdateItem", "Updated ${foundItem.name} count to $updatedCount and price to $updatedPrice")

                                isCheck = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                        .let {
                            if (!isCheck && previewMap.isNotEmpty()) {
                                it.border(2.dp, Yellow, CircleShape)
                            } else {
                                it // return the original modifier if condition not met
                            }
                        }
                ) {
                    Text(
                        "x3",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {
                        if (isCheck == false){
                            val foundItem = previewMap.values.firstOrNull()  // Assuming you want to update the first item in the previewMap for simplicity
                            if (foundItem != null) {
                                val updatedCount = foundItem.count + 3
                                val updatedPrice = updatedCount * foundItem.price  // Update the price accordingly

                                // Update the previewMap with the new count and price
                                previewMap[foundItem.productId] = foundItem.copy(
                                    count = updatedCount,
                                    price = updatedPrice
                                )

                                // Optionally, log the changes for debugging
                                Log.d("UpdateItem", "Updated ${foundItem.name} count to $updatedCount and price to $updatedPrice")

                                isCheck = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                        .let {
                            if (!isCheck && previewMap.isNotEmpty()) {
                                it.border(2.dp, Yellow, CircleShape)
                            } else {
                                it // return the original modifier if condition not met
                            }
                        }
                ) {
                    Text(
                        "x4",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }



                Button(
                    onClick = {
                        if (isCheck == false){
                            val foundItem = previewMap.values.firstOrNull()  // Assuming you want to update the first item in the previewMap for simplicity
                            if (foundItem != null) {
                                val updatedCount = foundItem.count + 4
                                val updatedPrice = updatedCount * foundItem.price  // Update the price accordingly

                                // Update the previewMap with the new count and price
                                previewMap[foundItem.productId] = foundItem.copy(
                                    count = updatedCount,
                                    price = updatedPrice
                                )

                                // Optionally, log the changes for debugging
                                Log.d("UpdateItem", "Updated ${foundItem.name} count to $updatedCount and price to $updatedPrice")

                                isCheck = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                        .let {
                            if (!isCheck && previewMap.isNotEmpty()) {
                                it.border(2.dp, Yellow, CircleShape)
                            } else {
                                it // return the original modifier if condition not met
                            }
                        }
                ) {
                    Text(
                        "x5",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {
                        if (isCheck == false){
                            val foundItem = previewMap.values.firstOrNull()  // Assuming you want to update the first item in the previewMap for simplicity
                            if (foundItem != null) {
                                val updatedCount = foundItem.count + 9
                                val updatedPrice = updatedCount * foundItem.price  // Update the price accordingly

                                // Update the previewMap with the new count and price
                                previewMap[foundItem.productId] = foundItem.copy(
                                    count = updatedCount,
                                    price = updatedPrice
                                )

                                // Optionally, log the changes for debugging
                                Log.d("UpdateItem", "Updated ${foundItem.name} count to $updatedCount and price to $updatedPrice")

                                isCheck = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                        .let {
                            if (!isCheck && previewMap.isNotEmpty()) {
                                it.border(2.dp, Yellow, CircleShape)
                            } else {
                                it // return the original modifier if condition not met
                            }
                        }
                ) {
                    Text(
                        "x10",
                        fontSize = 12.sp,
                        color = Color.Black
                    )
                }
                Button(
                    onClick = {
                        if (isCheck == false){
                            val foundItem = previewMap.values.firstOrNull()  // Assuming you want to update the first item in the previewMap for simplicity
                            if (foundItem != null) {
                                val updatedCount = foundItem.count + 49
                                val updatedPrice = updatedCount * foundItem.price  // Update the price accordingly

                                // Update the previewMap with the new count and price
                                previewMap[foundItem.productId] = foundItem.copy(
                                    count = updatedCount,
                                    price = updatedPrice
                                )

                                // Optionally, log the changes for debugging
                                Log.d("UpdateItem", "Updated ${foundItem.name} count to $updatedCount and price to $updatedPrice")

                                isCheck = true
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(Gray),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .size(35.dp) // Ukuran lingkaran
                        .clip(CircleShape) // Membuat bentuk lingkaran
                        .let {
                            if (!isCheck && previewMap.isNotEmpty()) {
                                it.border(2.dp, Yellow, CircleShape)
                            } else {
                                it // return the original modifier if condition not met
                            }
                        }
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
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        "Preview",
                        fontSize = 17.sp,
                        fontWeight = FontWeight(700),
                        modifier = Modifier
                    )
                    Spacer(Modifier.size(20.dp))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color(0xFFFFEFA7), shape = RoundedCornerShape(10.dp))
                            .border(4.dp, Yellow, shape = RoundedCornerShape(10.dp))
                            .padding(10.dp)
                            .heightIn(min = 23.dp)
                    ){
                        if (!isCheck){
                            Text(
                                lastScannedProduct?.name ?: "",
                                fontFamily = Digital,
                                fontSize = 10.sp,
                                color = Color.Black
                            )
                        }
                    }
                }
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
                                Log.d("kasieerrrrr", map.toString())
                                totalPrice += map.price
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    // Safe access and proper casting
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

                    val coroutineScope = rememberCoroutineScope()
                    val context = LocalContext.current

                    Column(
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier

                            .fillMaxSize()
                            .height(50.dp)

                    ){
                        Text(
                            "Total: $totalPrice",
                            fontSize = 15.sp,
                            fontWeight = FontWeight(700)
                        )
                        Spacer(Modifier.size(10.dp))


                        Button(
                            onClick = {
                                coroutineScope.launch {
                                    previewMap.values.forEach { itemPreview ->
                                        val kodeProduk = itemPreview.productId
                                        val count = itemPreview.count
                                        val itemRef = itemsCollection.document(kodeProduk)
                                        try {
                                            itemRef
                                                .update("stock", FieldValue.increment(-count.toLong()))
                                                .await()
                                            Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show()
                                        } catch (e: Exception) {
                                        }
                                    }
                                    previewMap.clear()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Yellow),
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier
                                .width(70.dp)
                                .height(25.dp)
                                .clip(RoundedCornerShape(10.dp)) // Membuat bentuk lingkaran
                        ) {
                            Text(
                                "Next",
                                color = Color.Black,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}