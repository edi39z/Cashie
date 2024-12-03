package com.example.myapplication.views.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.myapplication.user.User

@Composable
fun ProfilePage(user: User, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto Profil
        if (user.photoUrl.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(user.photoUrl),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp)
                    .background(Color.Gray, CircleShape),
            )
        } else {
            // Placeholder jika tidak ada foto
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(8.dp)
                    .background(Color.Gray, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Image",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nama
        Text(
            text = if (user.name.isNotEmpty()) user.name else "Unknown User",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Toko
        Text(
            text = "Toko: ${user.toko.ifEmpty { "Not Available" }}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Tanggal Lahir
        Text(
            text = "Tanggal Lahir: ${user.dateOfBirth.ifEmpty { "Not Available" }}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Gender
        Text(
            text = "Gender: ${user.gender.ifEmpty { "Not Specified" }}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        Text(
            text = "Email: ${user.email.ifEmpty { "Not Provided" }}",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Tombol Edit
        Button(
            onClick = { navController.navigate("register") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(Color(0xFFFDDE55))
        ) {
            Text(text = "Edit", fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}
