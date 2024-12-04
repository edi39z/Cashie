package com.example.myapplication.views.home

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.R
import com.example.myapplication.navbar.BottomBarScreen
import com.example.myapplication.product.ProductViewModel
import com.example.myapplication.ui.theme.Blue
import com.example.myapplication.views.profile.ProfileViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun HomePage(
    navMainController: NavController,
    profileViewModel: ProfileViewModel,
    navController: NavController,
    productViewModel: ProductViewModel
) {
    val products by productViewModel.products.collectAsState()
    val userState = profileViewModel.user.collectAsState(initial = null)
    val user = userState.value
    val photoProfile = Firebase.auth.currentUser?.photoUrl.toString()
    Log.d("HomePage", photoProfile)

    LaunchedEffect(Unit) {
        productViewModel.listenToProducts()
    }

    val productSize = products.size

    Column(
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
                        .clip(CircleShape)
                        .align(Alignment.CenterStart)
                        .background(Color.Gray, CircleShape)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterStart)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "N/A",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.cashie),
                contentDescription = "Cashie Logo",
                modifier = Modifier
                    .width(70.dp)
                    .align(Alignment.Center)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 41.dp, vertical = 31.dp)
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Welcome,",
                    style = TextStyle(fontWeight = FontWeight(300), fontSize = 15.sp)
                )
                Text(
                    text = user?.name ?: "Guest",
                    style = TextStyle(fontWeight = FontWeight(700), fontSize = 27.sp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = user?.toko ?: "No Shop Found",
                    style = TextStyle(
                        fontWeight = FontWeight(700),
                        fontSize = 16.sp,
                        color = Color(0x7A303131)
                    )
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White, shape = RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp))
                .padding(vertical = 56.dp, horizontal = 30.dp)
                .verticalScroll(rememberScrollState())
        ) {

            val gradientBrush = Brush.linearGradient(
                colors = listOf(
                    Color(0xFF26A6C0), // Warna awal
                    Color(0xFF68D2E8)  // Warna akhir
                )
            )
//
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                modifier = Modifier
//                    .background(
//                        brush = gradientBrush,
//                        shape = RoundedCornerShape(16.dp)
//                    )
//                    .padding(28.dp, 20.dp)
//                    .fillMaxWidth()
//                    .clip(RoundedCornerShape(16.dp))
//                    .clickable {
//                        navMainController.navigate(BottomBarScreen.Cashier.route)
//                    },
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .weight(0.7f) // Memberikan ruang proporsional untuk kolom
//                ) {
//                    Text(
//                        text = "Cashier",
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.Black
//                    )
//                    Text(
//                        text = "Let's Get Started! Begin a New Transaction",
//                        fontSize = 11.sp,
//                    )
//                }
//                Image(
//                    painter = painterResource(R.drawable.group),
//                    contentDescription = "Icon",
//                    modifier = Modifier
//                        .size(90.dp)
//                        .weight(0.3f), // Memberikan ruang proporsional untuk gambar
//                    contentScale = ContentScale.Fit // Hindari pemotongan gambar
//                )
//            }


            CustomBox(
                title = "Cashier",
                subtitle = "Let's Get Started! Begin a New Transaction",
                imageRes = R.drawable.group,
                onClick = {
                    navController.navigate(BottomBarScreen.Cashier.route)}
            )

            Spacer(modifier = Modifier.height(49.dp))

            Image(
                painter = painterResource(R.drawable.line_home),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(49.dp))

            CustomBox(
                title = "Database",
                subtitle = "$productSize Products",
                imageRes = R.drawable.database_img,
                onClick = { navController.navigate(BottomBarScreen.Databases.route) }
            )

            Spacer(modifier = Modifier.height(35.dp))

            CustomBox(
                title = "Sales History",
                subtitle = "--",
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
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Blue)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFC4E8F0))
                .padding(28.dp, 20.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(65.dp)
            )
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
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
                textAlign = TextAlign.End
            )
        }
    }
}
