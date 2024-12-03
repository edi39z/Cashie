package com.example.myapplication.views.casier

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Gray
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


data class Produk(
    val kode : String = "",
    val nama : String = "",
    val stok : Int = 0,
    val harga : Int = 0
)

data class PreviewProduk(
    val kode: String,
    val nama: String,
    val jumlah: Int,
    val harga: Double
)


@SuppressLint("LogNotTimber")
@Composable
fun kasir(){


    val items by remember { mutableStateOf(mutableMapOf<String, Produk>()) }
    val db = Firebase.firestore
    val itemsCollection = db.collection("items")

    LaunchedEffect(Unit) {
        itemsCollection
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val item = document.toObject(Produk::class.java)
                    items[item.kode] = item
                }
            }
            .await()
    }

    val previewList by remember {
        mutableStateOf(
            mutableListOf<PreviewProduk>()
        )
    }

    var kodeBarang by remember { mutableStateOf("") }
    var isCheck by remember { mutableStateOf(true) }


    Column {
        TextField(
            value = kodeBarang,
            onValueChange = { input ->
                kodeBarang = input // Update kodeBarang dengan nilai baru dari pengguna
                Log.d("KasirView", kodeBarang)
                val foundItem = items[kodeBarang]
                Log.d("KasirView", foundItem.toString())

                // Jika ditemukan, lakukan sesuatu
                foundItem?.let {
                    Log.d("KasirView","Let: ${it.nama}")
                    previewList.add(
                        PreviewProduk(
                            kode = it.kode,
                            nama = it.nama,
                            jumlah = 1,
                            harga = it.harga.toDouble()
                        )
                    )
                }

            },
            trailingIcon = {
                IconButton(
                    onClick = {
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
//                            previewList[0]["jumlah"] = (previewList[0]["jumlah"] as Int)*2
                            isCheck = true
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
//                Button(
//                    onClick = {
//                        previewList.forEach { map ->
//                            item[0]["stok"] = (item[0]["stok"] as Int) - (map["jumlah"] as Int)
//                        }
//                    },
//                    colors = ButtonDefaults.buttonColors(Yellow),
//                    contentPadding = PaddingValues(0.dp),
//                    modifier = Modifier
//                        .width(70.dp)
//                        .height(20.dp)
//                        .clip(RoundedCornerShape(10.dp)) // Membuat bentuk lingkaran
//                        .align(Alignment.BottomEnd)
//                ) {
//                    Text(
//                        "Next  ${item[0]["stok"]}",
//                        color = Color.Black,
//                        fontSize = 10.sp
//                    )
//                }
            }
        }
    }

}

@Preview (showBackground = true)
@Composable
fun kasirPreview(){
    kasir()
}