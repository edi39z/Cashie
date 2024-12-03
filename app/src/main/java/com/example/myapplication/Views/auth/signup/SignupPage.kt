package com.example.myapplication.views.auth.signup;

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
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.myapplication.ui.theme.button


@Composable
fun SignupPage(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val viewModel = SignUpViewModel(LocalContext.current)
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val passwordVisibility by viewModel.isPasswordVisible.collectAsState()
    val confirmPassword by viewModel.confirmPassword.collectAsState()
    val confirmPasswordVisibility by viewModel.isConfirmPasswordVisible.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo
        Image(
            painter = painterResource(id = R.drawable.cashie),
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Card for Signup Form
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
                    text = "Create Your Account",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(8.dp))

                // Input Email
                OutlinedTextField(
                    singleLine = true,
                    value = email,
                    onValueChange = { viewModel.updateEmail(it) },
                    label = { Text("Email",color = Color.Gray,fontSize = 13.sp) },
                    placeholder = { Text("Enter your username") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Email, contentDescription = "Email Icon")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Input Password
                OutlinedTextField(
                    singleLine = true,
                    value = password,
                    onValueChange = { viewModel.updatePassword(it) },
                    label = { Text("Password",color = Color.Gray,fontSize = 13.sp) },
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
                    shape = RoundedCornerShape(16.dp),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                )
                Spacer(modifier = Modifier.height(8.dp))

                // confi Password
                OutlinedTextField(
                    singleLine = true,
                    value = confirmPassword,
                    onValueChange = { viewModel.updateConfirmPassword(it) },
                    label = { Text("Confirm Password",color = Color.Gray, fontSize = 13.sp)},
                    placeholder = { Text("Confirm Password") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Lock, contentDescription = "Lock Icon")
                    },
                    trailingIcon = {
                        IconButton(onClick = { viewModel.toggleConfirmPasswordVisibility() }) {
                            Icon(
                                imageVector = if (confirmPasswordVisibility) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "Password Visibility"
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                )

                Spacer(modifier = Modifier.height(36.dp))
                val scope = rememberCoroutineScope()
                // Tombol sigup
                Button(
                    onClick = {
                        viewModel.signUpWithEmailAndPassword(
                            onLoginSuccess = {

                                navController.navigate("register")
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03AED2)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Create Account", color = Color.White)
                }



                Spacer(modifier = Modifier.height(50.dp))

                // Already have an account? Login
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 0.dp))
                {
                    Text(text = "Already have an account?",color = Color.Gray, fontSize = 13.sp)
                    TextButton(
                        onClick = { navController.navigate("login") },
                        contentPadding = PaddingValues(0.dp)
                    ) {
                        Text(text = "Login", fontSize = 13.sp ,color =(button), fontWeight = FontWeight.Bold)
                    }
                }
            }

        }
    }
}