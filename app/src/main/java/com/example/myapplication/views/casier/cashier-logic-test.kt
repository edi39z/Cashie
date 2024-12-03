package com.example.myapplication.views

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.product.Product
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Gray
import com.example.myapplication.ui.theme.Yellow
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

@SuppressLint("LogNotTimber")
@Composable
fun Kasir(){

    val db = Firebase.firestore
    val items by remember { mutableStateOf(mutableMapOf<String, Product>()) }
    val itemsCollection = db.collection("users").document("KxrhNm3yfzb4GUoEGjb2v1IJcue2").collection("products")

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
            Log.d("KasirView", "Items berhasil dimuat: $items")
        } catch (e: Exception) {
            Log.e("KasirView", "Gagal memuat data: ${e.message}", e)
        }
    }

//    val previewList by remember {
//        mutableStateOf(
//            mutableListOf<PreviewProduk>()
//        )
//    }

    val previewList = remember { mutableStateListOf<PreviewProduk>() }


    var kodeBarang by remember { mutableStateOf("") }
    var isCheck by remember { mutableStateOf(false) }

    Column {
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
                        Log.d("Kasir2", "Found Item: $foundItem")
                        val checkPreviewList = previewList.find { it.kode == kodeBarang }
                        Log.d("KasirView", foundItem.toString())

                        foundItem?.let {
                            Log.d("Kasir2", "Let: ${it.nama_produk}")
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
                            } else {
                                // Cari index elemen di previewList dan update jumlahnya
                                val index = previewList.indexOf(checkPreviewList)
                                if (index >= 0) {
                                    previewList[index] = checkPreviewList.copy(jumlah = checkPreviewList.jumlah + 1)
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
            modifier = Modifier
                .fillMaxWidth()
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Tombol di kiri
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.weight(1f)
            ) {
                Button(
                    onClick = {
                        if (isCheck == false){
                            previewList.lastOrNull()?.let {
                                it.jumlah++
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
                            previewList.lastOrNull()?.let {
                                it.jumlah += 2
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
                            previewList.lastOrNull()?.let {
                                it.jumlah += 3
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
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = Modifier
                .weight(2f)
        ){
            Text("Preview",
                fontWeight = FontWeight(700)
            )
            Box(
                modifier = Modifier
                    .background(color = Blue, shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp))
                    .padding(14.dp)
                    .fillMaxSize()
            ){
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
                        Log.d("KasirView", map.toString())
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

                }

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
                                        Log.d("Kasir2", "Stock updated successfully for $kodeProduk")
                                    } catch (e: Exception) {
                                        Log.e("Kasir2", "Error updating stock for $kodeProduk: ${e.message}", e)
                                    }
                                } else {
                                    Log.w("Kasir2", "Item with kode $kodeProduk not found.")
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
                        .align(Alignment.BottomEnd)
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

@Preview (showBackground = true)
@Composable
fun KasirPreview(){
    Kasir()
}