package com.example.myapplication.views.database

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    MaterialTheme {
        HomeScreen(username = "John Doe", toko = "Toko A")
    }
}

@Composable
fun HomeScreen(username: String, toko: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF68D2E8))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HeaderSection()
            GreetingSection(username, toko)
            BottomSheet()

        }

    }
}

@Composable
fun HeaderSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Gambar profil di kiri
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_camera),
            contentDescription = "Foto Profil",
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray, CircleShape)
                .align(Alignment.CenterStart) // Posisi di kiri
        )

        // Gambar utama di tengah
        Image(
            painter = painterResource(id = R.drawable.cashie),
            contentDescription = "cashie logo",
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Center) // Posisi di tengah
        )
    }
}

@Composable
fun GreetingSection(username: String, toko: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                // Baris pertama
                append("Halo,\n")

                // Baris kedua
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                ) {
                    append("$username\n")
                }

                // Baris ketiga
                withStyle(
                    style = SpanStyle(
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                ) {
                    append(toko)
                }
            },
            fontSize = 18.sp // Ukuran font default untuk baris pertama
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet() {
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            BottomSheetContent(
                modifier = Modifier
                    .fillMaxHeight(0.5f) // Mengatur sheet maksimal tinggi 50% dari layar
            )        },
        sheetPeekHeight = 600.dp
    ) {
    }

}

@Composable
fun BottomSheetContent(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Box dengan ukuran dan warna yang bisa diubah
        Box(
            modifier = Modifier
                .width(300.dp) // Mengatur lebar Box
                .height(100.dp) // Mengatur tinggi Box
                .background(color = Color(0xFF68D2E8), shape = RoundedCornerShape(16.dp)) // Mengubah warna dan bentuk
                .padding(16.dp) // Padding di dalam Box
        ) {
            // Row untuk meletakkan teks dan gambar secara horizontal di tengah
            Row(
                verticalAlignment = Alignment.CenterVertically, // Pusatkan secara vertikal
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Beri jarak antar elemen
            ) {
                // Teks
                Text(
                    text = buildAnnotatedString {
                        // Baris pertama dengan ukuran teks dan warna yang dapat diubah
                        withStyle(
                            style = SpanStyle(
                                fontSize = 28.sp, // Ubah ukuran teks baris pertama di sini
                                color = Color.Black, // Ubah warna teks baris pertama di sini
                                fontWeight = FontWeight.Bold // Membuat teks menjadi bold
                            )
                        ) {
                            append("Cashier\n")
                        }

                        // Baris kedua dengan ukuran teks dan warna yang dapat diubah
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp, // Ubah ukuran teks baris kedua di sini
                                color = Color.Gray // Ubah warna teks baris kedua di sini
                            )
                        ) {
                            append("Let's get started!")
                        }
                    },
                    color = Color.White // Warna default jika tidak ada gaya
                )

                Spacer(modifier = Modifier.width(16.dp)) // Mengurangi jarak antar elemen teks dan gambar

                // Menampilkan gambar di samping teks
                Image(
                    painter = painterResource(id = R.drawable.group),
                    contentDescription = "Gambar tengah",
                    modifier = Modifier
                        .size(100.dp) // Membuat gambar lebih besar
                        .offset(y = -30.dp) // Mengatur offset untuk menggambar sebagian keluar dari box
                )
            }
        }
        // Divider (Garis pemisah) antara box dan Additional Content
        Spacer(modifier = Modifier.height(32.dp))

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp), // Memberikan jarak vertikal pada divider
            color = Color.Gray, // Warna garis pemisah
            thickness = 1.dp // Ketebalan garis pemisah
        )

        Spacer(modifier = Modifier.height(32.dp))

        val productCount = 10 // Contoh jumlah produk yang tersedia

        Box(
            modifier = Modifier
                .width(300.dp) // Lebar Box
                .height(100.dp) // Tinggi Box
                .background(color = Color(0xFF68D2E8), shape = RoundedCornerShape(16.dp)) // Warna dan bentuk background
        ) {
            Row(
                modifier = Modifier.fillMaxSize(), // Mengisi ukuran Box sepenuhnya
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start // Menjaga elemen berada di sisi kiri
            ) {
                // Gambar
                Box(
                    modifier = Modifier
                        .width(100.dp) // Lebar Box untuk gambar
                        .height(100.dp) // Tinggi Box untuk gambar
                        .background(
                            color = Color(0xFFD3EAF7),
                            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.database_img),
                        contentDescription = "Gambar kiri",
                        modifier = Modifier.size(65.dp) // Ukuran gambar lebih kecil
                    )
                }

                Spacer(modifier = Modifier.width(12.dp)) // Jarak antar gambar dan teks

                // Kolom untuk teks dan tombol
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f) // Membuat kolom mengambil ruang yang tersisa
                        .padding(vertical = 12.dp), // Padding vertikal
                    verticalArrangement = Arrangement.SpaceBetween // Distribusi elemen secara vertikal
                ) {
                    // Teks "Sales History"
                    Text(
                        text = buildAnnotatedString {
                            // Baris pertama
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Database\n")
                            }

                            // Baris kedua
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            ) {
                                append("$productCount products")
                            }
                        },
                        modifier = Modifier.align(Alignment.Start) // Memastikan teks sejajar ke kiri
                    )

                    // Tombol di bawah teks
                    Button(
                        onClick = { /* Action tombol */ },
                        modifier = Modifier
                            .width(80.dp)
                            .height(30.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFDDE55)) // Warna tombol
                    ) {
                        Text(
                            text = "Edit",
                            fontSize = 12.sp, // Ukuran font
                            color = Color.Black
                        )
                    }
                }
            }
        }





        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .width(300.dp) // Lebar Box
                .height(100.dp) // Tinggi Box
                .background(color = Color(0xFF68D2E8), shape = RoundedCornerShape(16.dp)) // Warna dan bentuk background
        ) {
            Row(
                modifier = Modifier.fillMaxSize(), // Mengisi ukuran Box sepenuhnya
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start // Menjaga elemen berada di sisi kiri
            ) {
                // Gambar
                Box(
                    modifier = Modifier
                        .width(100.dp) // Lebar Box untuk gambar
                        .height(100.dp) // Tinggi Box untuk gambar
                        .background(
                            color = Color(0xFFD3EAF7),
                            shape = RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.history_image),
                        contentDescription = "Gambar kiri",
                        modifier = Modifier.size(65.dp) // Ukuran gambar lebih kecil
                    )
                }

                Spacer(modifier = Modifier.width(12.dp)) // Jarak antar gambar dan teks

                // Kolom untuk teks dan tombol
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f) // Membuat kolom mengambil ruang yang tersisa
                        .padding(vertical = 12.dp), // Padding vertikal
                    verticalArrangement = Arrangement.SpaceBetween // Distribusi elemen secara vertikal
                ) {
                    // Teks "Sales History"
                    Text(
                        text = buildAnnotatedString {
                            // Baris pertama
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 18.sp,
                                    color = Color.Black,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Sales History\n")
                            }

                            // Baris kedua
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            ) {
                                append("tanggal")
                            }
                        },
                        modifier = Modifier.align(Alignment.Start) // Memastikan teks sejajar ke kiri
                    )

                    // Tombol di bawah teks
                    Button(
                        onClick = { /* Action tombol */ },
                        modifier = Modifier
                            .width(80.dp)
                            .height(30.dp),
                        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFFFDDE55)) // Warna tombol
                    ) {
                        Text(
                            text = "Edit",
                            fontSize = 12.sp, // Ukuran font
                            color = Color.Black
                        )
                    }
                }
            }
        }

        }
    }







@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp()
}
