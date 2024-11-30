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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.navbar.BottomBarScreen
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.ui.theme.Gray
import com.example.myapplication.views.auth.`fun`.AuthManager
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun HomePage(navMainController: NavController) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Blue)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 40.dp, 21.dp)
            ) {
                Image(
                    painter = painterResource(id = android.R.drawable.ic_menu_camera),
                    contentDescription = "Foto Profil",
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.Gray, CircleShape)
                        .align(Alignment.CenterStart)
                )
                Image(
                    painter = painterResource(id = R.drawable.cashie),
                    contentDescription = "cashie logo",
                    modifier = Modifier
                        .width(70.dp)
                        .align(Alignment.Center)
                )
                val context = LocalContext.current
                // Icon Keluar
                androidx.compose.material.Icon(

                    painter = painterResource(id = R.drawable.logout),
                    contentDescription = "Keluar",
                    modifier = Modifier.size(24.dp).clickable {
                        AuthManager(context).signOut()
                        Log.d("cobs", "$navMainController")
                        navMainController?.navigate("login")

                    }
                        .align(Alignment.CenterEnd),
                    tint = Color.Black
                )
            }
            
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(41.dp, 31.dp)
            ){
                Row(modifier = Modifier
                    .fillMaxWidth()
                ){
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight(300),
                                    fontSize = 15.sp,
                                )
                            ) {
                                append("Welcome,\n")
                            }
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 27.sp,
                                    fontWeight = FontWeight(700)
                                )
                            ) {
                                append("Aqilla Zia Maira\n") // Nama pengguna
                            }
                            append("\n")
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight(700),
                                    fontSize = 16.sp,
                                    color = Color(0x7A303131)
                                )
                            ) {
                                append("UD. SEJAHTERA UTAMA") // Nama toko
                            }
                        }
                    )
                }

            }
            // Konten HomePage
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.White, shape = RoundedCornerShape(40.dp,40.dp,0.dp,0.dp))
                    .padding(vertical = 56.dp, horizontal = 30.dp)

            ) {
                CustomBox(
                    title = "Cashier",
                    subtitle = "Let's Get Started! Begin a New Transaction",
                    imageRes = R.drawable.group,
                    onClick = { navMainController.navigate(BottomBarScreen.Cashier.route) },
                )

                Spacer(modifier = Modifier.height(49.dp))

                Image(
                    painter = painterResource(R.drawable.line_home),
                    contentDescription = "tes"
                )

                Spacer(modifier = Modifier.height(49.dp))

                CustomBox(
                    title = "Database",
                    subtitle = "15 Products",
                    imageRes = R.drawable.database_img,
                    onClick = { navMainController.navigate(BottomBarScreen.Databases.route) }
                )

                Spacer(modifier = Modifier.height(35.dp))

                CustomBox(
                    title = "Sales History",
                    subtitle = "15/02/2024",
                    imageRes = R.drawable.history_image,
                    onClick = { navMainController.navigate(BottomBarScreen.History.route) }
                )
            }
        }
}

@Composable
fun CustomBox(title: String, subtitle: String, imageRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .background(
                color = Blue,
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .background(Color(0xFFC4E8F0))
                .padding(28.dp,20.dp)
        ){
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Icon",
                modifier = Modifier
                    .size(65.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end=20.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomePagePreview() {
    HomePage(navMainController = NavController(LocalContext.current))
}