package com.example.myapplication.Views

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Gray
import com.example.myapplication.ui.theme.Yellow

@Composable
fun kasir(){
//    private val db = FirebaseFirestore.getInstance()
    val item = mapOf(
        "kode" to "0",
        "nama" to "indomie",
        "stok" to 10,
        "harga" to 3000
    )
    val preview = listOf(
        mutableMapOf("nama" to "indomie", "jumlah" to null, "harga" to 3000),
        mutableMapOf("kode" to "0", "nama" to "indomie", "jumlah" to null, "harga" to 3000),
        mutableMapOf("kode" to "0", "nama" to "indomie", "jumlah" to null, "harga" to 3000)
    )

    var kodeBarang by remember { mutableStateOf("") }
    var isCheck by remember { mutableStateOf(true) }


    Column {
        TextField(
            value = kodeBarang,
            onValueChange = {
                if (kodeBarang == item["kode"]){
                    preview[0]["nama"] = item["nama"]
                    preview[0]["jumlah"] = 1
                    isCheck = false
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
                            preview[0]["jumlah"] = (preview[0]["jumlah"] as Int)*2
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
                        "x3",
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
//                    for (i in previewList) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween
//                        ) {
//                            Text(
//                                text = kodeBarang.name,
//                                fontSize = 9.sp,
//                            )
//                            Text(
//                                kodeBarang.jumlah,
//                                fontSize = 9.sp
//                            )
//                            Text(
//                                kodeBarang.harga,
//                                fontSize = 9.sp
//                            )
//                        }
//                    }
                }
                Button(
                    onClick = {},
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
fun kasirPreview(){
    kasir()
}