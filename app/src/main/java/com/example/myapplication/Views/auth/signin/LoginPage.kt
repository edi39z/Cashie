package com.example.myapplication.views.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.textLogo
import com.example.myapplication.ui.theme.textbutton

@Composable

fun LoginPage(
    modifier : Modifier = Modifier,
    navController: NavController,
) {
    val viewModel = LoginViewModel(LocalContext.current)
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val passwordVisibility by viewModel.isPasswordVisible.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.cashie),
            contentDescription = "App Logo",
            modifier = Modifier.size(120.dp)
        )

        Text(
            text = stringResource(R.string.app_name),
            fontSize = 28.sp,
            fontFamily = FontFamily.Cursive,
            fontWeight = FontWeight.Black,
            color = textLogo,
            style = TextStyle(
                shadow = Shadow(
                    color = textLogo,
                    offset = Offset(2f, 2f),

                    )
            )
        )


        Spacer(modifier = Modifier.height(24.dp))

        // Card untuk Konten Login
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp) // Perbaikan Elevas
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Login",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Input Email
                OutlinedTextField(
                    value = email,
                    onValueChange = { viewModel.updateEmail(it)},
                    label = { Text("Username ID", color = Color.Gray,fontSize = 13.sp) },
                    placeholder = { Text("Enter your username") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Input Password
                OutlinedTextField(
                    value = password,
                    onValueChange = { viewModel.updatePassword(it)},
                    label = { Text("Password", color = Color.Gray,fontSize = 13.sp) },
                    placeholder = { Text("Password") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon")
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.togglePasswordVisibility() }) {
                            Icon(
                                imageVector = if (passwordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Password Visibility"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(50)
                )
                Spacer(modifier = Modifier.height(16.dp))
                //reset password


                Spacer(modifier = Modifier.height(16.dp))

                // Tombol Login
                Button(
                    onClick = {
                        viewModel.loginWithEmailAndPassword {
                            navController.navigate("home")
                        }
                    },
//                    enabled = authState.value != AuthState.Loading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A5D44)),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = "Login",
                        color = Color.White ,
                        fontSize = 18.sp,
//                        fontFamily = fontFamily
                    )
                }



                Spacer(modifier = Modifier.height(30.dp))

                //Login dengan Third Party
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    HorizontalDivider(modifier = Modifier.weight(1f))
                    Text(
                        text = " or Continue with ",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 8.dp),
                        color = Color.Gray
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = {
                        viewModel.signInWithGoogle {
                            navController.navigate("home")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.google),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp).padding(end = 8.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.google_sign_in),
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                // Text untuk Sign Up
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 0.dp)
                ) {
                    Text(text = "Don't have an account?",color = Color.Gray, fontSize = 13.sp)
                    TextButton(
                        onClick = { navController.navigate("signup") },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "Sign Up", fontSize = 13.sp ,color = (textbutton), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
