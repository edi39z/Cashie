package com.example.myapplication.views.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.R
import com.example.myapplication.navbar.BottomBarScreen
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.views.auth.`fun`.AuthManager
import com.example.myapplication.views.profile.ProfileViewModel
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun HomePage(navMainController: NavController, profileViewModel: ProfileViewModel,navController: NavController) {
    // Mendapatkan data pengguna dari StateFlow
    val userState = profileViewModel.user.collectAsState(initial = null)
    val user = userState.value
    val photoProfile = Firebase.auth.currentUser?.photoUrl.toString()
    Log.d("HomePage", photoProfile)

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
                .padding(horizontal = 40.dp, vertical = 21.dp)
        ) {
            // Foto Profil
            if (photoProfile.isNotEmpty()) {
                AsyncImage(
                    model = photoProfile,
                    contentDescription = "Foto Profil",
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.Gray, CircleShape)
                        .align(Alignment.CenterStart)
                        .clip(CircleShape),
                )
            } else {
                // Placeholder jika tidak ada foto
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.Gray, CircleShape)
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "N/A",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            // Logo
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
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        AuthManager(context).signOut()
                        navMainController.navigate("login")
                    }
                    .align(Alignment.CenterEnd),
                tint = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 41.dp, vertical = 31.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Nama pengguna dan nama toko
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
                            append(user?.name ?: "Guest\n") // Nama pengguna
                        }
                        append("\n")
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight(700),
                                fontSize = 16.sp,
                                color = Color(0x7A303131)
                            )
                        ) {
                            append(user?.toko ?: "No Shop Found") // Nama toko
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
                    onClick = { navController.navigate(BottomBarScreen.Cashier.route) },
                )

                Spacer(modifier = Modifier.height(49.dp))

                Image(
                    painter = painterResource(R.drawable.line_home),
                    contentDescription = "tes"
                )

                Spacer(modifier = Modifier.height(49.dp))

                CustomBox(
                    title = "Database",
                    subtitle = "",
                    imageRes = R.drawable.database_img,
                    onClick = { navController.navigate(BottomBarScreen.Databases.route) }
                )

                Spacer(modifier = Modifier.height(35.dp))

                CustomBox(
                    title = "Sales History",
                    subtitle = "15/02/2024",
                    imageRes = R.drawable.history_image,
                    onClick = { navController.navigate(BottomBarScreen.History.route) }
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

