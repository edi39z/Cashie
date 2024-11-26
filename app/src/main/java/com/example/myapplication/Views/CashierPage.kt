package com.example.myapplication.Views

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
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Gray
import com.example.myapplication.ui.theme.Logo
import com.example.myapplication.ui.theme.Yellow


@Composable
fun CashierMenu() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        Text(
            text = "Cashie",
            fontSize = 30.sp,
            color = Logo,
        )
        Spacer(modifier = Modifier.size(30.dp))
        TextField(
            value = "Ketik manual kode",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Image(
            painter = painterResource(id = R.drawable.line),
            contentDescription = "or",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp,18.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.scan_img),
            contentDescription = "scanner",
            modifier = Modifier
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(Yellow),
            modifier = Modifier
                .offset(y=-30.dp)
                .size(70.dp) // Ukuran lingkaran
                .clip(CircleShape) // Membuat bentuk lingkaran
        ) {
            Icon(
                imageVector = Icons.Default.Face,
                contentDescription = "camera button",
                tint = Color.Black
            )
        }
        Column(modifier = Modifier
            .offset(y=-30.dp)
            .fillMaxWidth()
        ) {
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
                        onClick = {},
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
                }

                Spacer(Modifier.width(70.dp))

                // Tombol di kanan
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.weight(1f)
                ) {
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
                            "xn",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.size(20.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = Modifier
                        .weight(1f)
                ){
                    Text(
                        "Quick scan",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700)
                    )
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Gray),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(50.dp) // Ukuran lingkaran
                            .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
                            .background(Gray)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Indomie goreng",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Gray),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(50.dp) // Ukuran lingkaran
                            .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
                            .background(Gray)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Indomie goreng",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Gray),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(50.dp) // Ukuran lingkaran
                            .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
                            .background(Gray)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Indomie goreng",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Gray),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(50.dp) // Ukuran lingkaran
                            .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
                            .background(Gray)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Indomie goreng",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(Gray),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .height(50.dp) // Ukuran lingkaran
                            .clip(RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp))
                            .background(Gray)
                            .fillMaxWidth()
                    ) {
                        Text(
                            "Indomie goreng",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(18.dp),
                    modifier = Modifier
                        .weight(2f)
                ){
                    Text("Preview",
                        fontWeight = FontWeight(700))
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
                            for (i in 1..10) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        "Indomie Goreng",
                                        fontSize = 9.sp,
                                    )
                                    Text(
                                        "1",
                                        fontSize = 9.sp
                                    )
                                    Text(
                                        "3000",
                                        fontSize = 9.sp
                                    )
                                }
                            }
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


//            Column(
//                verticalArrangement = Arrangement.spacedBy(18.dp),
//                modifier = Modifier
//                    .weight(2f)){
//                Text("Preview")
//                Box(
//                    modifier = Modifier
//                        .background(color = Blue, shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp))
//                        .padding(14.dp)
//                        .fillMaxSize()
//                ){
//                    Button(
//                        onClick = {},
//                        colors = ButtonDefaults.buttonColors(Yellow),
//                        contentPadding = PaddingValues(0.dp),
//                        modifier = Modifier
//                            .width(70.dp)
//                            .height(20.dp)
//                            .clip(RoundedCornerShape(10.dp)) // Membuat bentuk lingkaran
//                            .align(Alignment.BottomEnd)
//                    ) {
//                        Text(
//                            "Next",
//                            color = Color.Black,
//                            fontSize = 10.sp
//                        )
//                    }
//                }
//            }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CashierMenuPreview() {
    CashierMenu()
}
