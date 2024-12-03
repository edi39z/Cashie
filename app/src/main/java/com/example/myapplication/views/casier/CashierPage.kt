package com.example.myapplication.views.casier

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.product.Product
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Gray
import com.example.myapplication.ui.theme.Logo
import com.example.myapplication.ui.theme.Yellow
import com.example.myapplication.views.PreviewProduk
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


data class PreviewProduk(
    val kode: String,
    val nama: String,
    var jumlah: Int,
    var harga: Double
)

@Composable
fun CashierPage() {

    val db = Firebase.firestore
    val items by remember { mutableStateOf(mutableMapOf<String, Product>()) }
    val previewList = remember { mutableStateListOf<PreviewProduk>() }
    var kodeBarang by remember { mutableStateOf("") }
    var isCheck by remember { mutableStateOf(false) }
    val itemsCollection = db.collection("users").document("KxrhNm3yfzb4GUoEGjb2v1IJcue2").collection("products")

    var totalHarga = 0.0

    // Menggunakan LaunchedEffect untuk memuat data
    LaunchedEffect(Unit) {
        try {
            val result = itemsCollection.get().await() // Menggunakan .await() langsung
            for (document in result.documents) {
                val item = document.toObject(Product::class.java)
                if (item != null) {
                    items[item.id_produk] = item
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
                        Log.d("Kasir2", "Kode Barang: $kodeBarang")
                        val foundItem = items[kodeBarang]
                        Log.d("kasiee", "Found Item: $foundItem")
                        val checkPreviewList = previewList.find { it.kode == kodeBarang }
                        Log.d("kasiee", foundItem.toString())

                        foundItem?.let {
                            Log.d("Kasiee", "Let: ${it.nama_produk}")
                            if (checkPreviewList == null) {
                                // Tambahkan item baru jika tidak ada di previewList
                                previewList.add(
                                    PreviewProduk(
                                        kode = it.id_produk,
                                        nama = it.nama_produk,
                                        jumlah = 1,
                                        harga = it.harga_produk.toDouble()
                                    )
                                )
                                isCheck = false
                            } else {
                                // Cari index elemen di previewList dan update jumlahnya
                                val index = previewList.indexOf(checkPreviewList)
                                if (index >= 0) {
                                    val updatedItem = checkPreviewList.copy(
                                        jumlah = checkPreviewList.jumlah + 1,
                                        harga = checkPreviewList.harga * (checkPreviewList.jumlah + 1)
                                    )
                                    previewList[index] = updatedItem
                                    isCheck = false
                                }
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
            onClick = {},
            colors = ButtonDefaults.buttonColors(Yellow),
            modifier = Modifier
//                .offset(y = -30.dp)
                .size(70.dp) // Ukuran lingkaran
                .clip(CircleShape) // Membuat bentuk lingkaran
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "camera button",
                tint = Color.Black
            )
        }
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                    Button(
                        onClick = {
                            if (isCheck == false){
                                val lastIndex = previewList.lastIndex
                                if (lastIndex >= 0) {
                                    val lastItem = previewList[lastIndex]
                                    previewList[lastIndex] = lastItem.copy(jumlah = lastItem.jumlah + 1)
                                    Log.d("Kasir2", previewList.toString())
                                    isCheck = true
                                }
                            }
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
                        onClick = {
                            if (isCheck == false){
                                val lastIndex = previewList.lastIndex
                                if (lastIndex >= 0) {
                                    val lastItem = previewList[lastIndex]
                                    previewList[lastIndex] = lastItem.copy(jumlah = lastItem.jumlah + 2)
                                    Log.d("Kasir2", previewList.toString())
                                    isCheck = true
                                }
                            }
                        },
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
                        onClick = {
                            if (isCheck == false){
                                val lastIndex = previewList.lastIndex
                                if (lastIndex >= 0) {
                                    val lastItem = previewList[lastIndex]
                                    previewList[lastIndex] = lastItem.copy(jumlah = lastItem.jumlah + 3)
                                    Log.d("Kasir2", previewList.toString())
                                    isCheck = true
                                }
                            }
                        },
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
                        onClick = {
                            if (isCheck == false){
                                val lastIndex = previewList.lastIndex
                                if (lastIndex >= 0) {
                                    val lastItem = previewList[lastIndex]
                                    previewList[lastIndex] = lastItem.copy(jumlah = lastItem.jumlah + 4)
                                    Log.d("Kasir2", previewList.toString())
                                    isCheck = true
                                }
                            }
                        },
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
                        onClick = {
                            if (isCheck == false){
                                val lastIndex = previewList.lastIndex
                                if (lastIndex >= 0) {
                                    val lastItem = previewList[lastIndex]
                                    previewList[lastIndex] = lastItem.copy(jumlah = lastItem.jumlah + 9)
                                    Log.d("Kasir2", previewList.toString())
                                    isCheck = true
                                }
                            }
                        },
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
                        onClick = {
                            var n = 49
                            if (isCheck == false){
                                val lastIndex = previewList.lastIndex
                                if (lastIndex >= 0) {
                                    val lastItem = previewList[lastIndex]
                                    previewList[lastIndex] = lastItem.copy(jumlah = lastItem.jumlah + n)
                                    Log.d("Kasir2", previewList.toString())
                                    isCheck = true
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Gray),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(35.dp) // Ukuran lingkaran
                            .clip(CircleShape) // Membuat bentuk lingkaran
                    ) {
                        Text(
                            "xn",
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
                        fontWeight = FontWeight(700)
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                color = Blue,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(14.dp)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            // Add your table here
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    "Nama",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(700)
                                )
                                Text(
                                    "Jumlah",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(700)
                                )
                                Text(
                                    "Harga",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(700)
                                )
                            }
                            Spacer(Modifier.size(8.dp))
                            previewList.forEach { map ->
                                Log.d("kasieerrrrr", map.toString())
                                totalHarga += map.harga
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    // Safe access and proper casting
                                    Text(
                                        text = map.nama, // Default to "Unknown" if null
                                        fontSize = 9.sp,
                                    )
                                    Text(
                                        text = map.jumlah.toString(), // Convert Int to String
                                        fontSize = 9.sp
                                    )
                                    Text(
                                        text = map.harga.toString(), // Convert Int to String
                                        fontSize = 9.sp
                                    )
                                }
                            }
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                            Text("tes")
                        }


                        Column (
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                        ){
                            Text(
                                text = "Total:  $totalHarga",
                                fontSize = 12.sp,
                                fontWeight = FontWeight(700)
                            )

                            Spacer(Modifier.size(20.dp))

                            val coroutineScope = rememberCoroutineScope()

                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        previewList.forEach { itemPreview ->
                                            val kodeProduk = itemPreview.kode
                                            val item = items[kodeProduk]

                                            if (item != null) {
                                                // Kurangi stok lokal
                                                item.stock_produk -= itemPreview.jumlah

                                                // Update di Firestore
                                                val itemRef = itemsCollection.document(kodeProduk)
                                                try {
                                                    itemRef.update("stock_produk", item.stock_produk).await()
                                                    Log.d("kasieerrrrr", "Stock updated successfully for $kodeProduk")
                                                } catch (e: Exception) {
                                                    Log.e("kasieerrrrr", "Error updating stock for $kodeProduk: ${e.message}", e)
                                                }
                                            } else {
                                                Log.w("kasieerrrrr", "Item with kode $kodeProduk not found.")
                                            }
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(Yellow),
                                contentPadding = PaddingValues(0.dp),
                                modifier = Modifier
                                    .width(70.dp)
                                    .height(20.dp)
                                    .clip(RoundedCornerShape(10.dp)) // Membuat bentuk lingkaran
                            ) {
                                Text(
                                    "Next",
                                    color = Color.Black,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }


    }
}


@Preview(showBackground = true)
@Composable
fun CashierMenuPreview() {
    CashierPage()
}
