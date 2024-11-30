package com.example.myapplication.views.home

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navbar.BottomBarScreen
import com.example.myapplication.views.auth.`fun`.AuthManager


@Composable
fun HomePage(navMainController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF68D2E8))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_camera),
                    contentDescription = "Foto Profil",
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.Gray, CircleShape)
                        .align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(id = R.drawable.cashie),
                    contentDescription = "cashie logo",
                    modifier = Modifier
                        .size(70.dp)
                        .align(Alignment.Center)
                )
            }

            // Greeting Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Welcome,\n")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                        ) {
                            append("Aqilla Zia Maira\n") // Nama pengguna
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 20.sp,
                                color = Color.Gray
                            )
                        ) {
                            append("UD. Sejahtera Utama") // Nama toko
                        }
                    },
                    fontSize = 18.sp
                )
            }
            val context = LocalContext.current
            // Icon Keluar
            androidx.compose.material.Icon(

                painter = painterResource(id = R.drawable.logout),
                contentDescription = "Keluar",
                modifier = Modifier.size(24.dp).clickable {
                    AuthManager(context).signOut()
                    Log.d("cobs", "$navMainController")
                    navMainController?.navigate("login")

                },
                tint = Color.Black
            )

            // Konten HomePage
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CustomBox(
                    title = "Cashier",
                    subtitle = "Let's Get Started! Begin a New Transaction",
                    imageRes = R.drawable.group,
                    onClick = { navMainController.navigate(BottomBarScreen.Cashier.route) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomBox(
                    title = "Database",
                    subtitle = "15 Products",
                    imageRes = R.drawable.database_img,
                    onClick = { navMainController.navigate(BottomBarScreen.Databases.route) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomBox(
                    title = "Sales History",
                    subtitle = "15/02/2024",
                    imageRes = R.drawable.history_image,
                    onClick = { navMainController.navigate(BottomBarScreen.History.route) }
                )
            }
        }
    }
}

@Composable
fun CustomBox(title: String, subtitle: String, imageRes: Int, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .width(300.dp)
            .height(100.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Icon",
                modifier = Modifier.size(65.dp)
            )
            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage(navMainController = NavController(LocalContext.current))
}