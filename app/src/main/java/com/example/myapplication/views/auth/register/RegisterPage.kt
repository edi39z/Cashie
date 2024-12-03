package com.example.myapplication.views.auth.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.Background
import com.example.myapplication.ui.theme.button
import com.example.myapplication.ui.theme.textbutton
import com.example.myapplication.ui.theme.textbutton2
import com.example.myapplication.user.SaveUserDataToFirestore
import com.example.myapplication.user.User
import com.google.firebase.auth.FirebaseAuth
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(modifier: Modifier = Modifier, navController: NavController) {
    val viewModel: RegisterViewModel = viewModel()
    val firstname by viewModel.firstname.collectAsState()
    val lastname by viewModel.lastname.collectAsState()
    val username by viewModel.username.collectAsState()
    val dateOfBirth by viewModel.dateOfBirth.collectAsState()
    val showDatePicker by viewModel.showDatePicker.collectAsState()
    val gender by viewModel.gender.collectAsState()
    val toko by viewModel.toko.collectAsState()

    val context = LocalContext.current
    val currentUser = FirebaseAuth.getInstance().currentUser




    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.cashie),
            contentDescription = null,
            modifier = Modifier.size(160.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedTextField(
                        value = firstname,
                        onValueChange = { viewModel.updateFirstname(it) },
                        label = { Text("First Name", fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedTextField(
                        value = lastname,
                        onValueChange = { viewModel.updateLastname(it) },
                        label = { Text("Last Name", fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        textStyle = LocalTextStyle.current.copy(color = Color.Black)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { viewModel.updateUsername(it) },
                    label = { Text("Username", fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black)
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = toko,
                    onValueChange = { viewModel.updateToko(it) },
                    label = { Text("Toko", fontSize = 13.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black)
                )

                Spacer(modifier = Modifier.height(8.dp))

// Tanggal lahir

                val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
                val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

                OutlinedTextField(
                    value = dateOfBirth,
                    onValueChange = {},
                    label = { Text("Date of Birth", color = Color.Black, fontSize = 13.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.updateShowDatePicker(true) },
                    shape = RoundedCornerShape(16.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black),
                    readOnly = true,
                    trailingIcon = {
                        IconButton(onClick = { viewModel.updateShowDatePicker(true) }) {
                            Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Black)
                        }
                    }
                )

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { viewModel.updateShowDatePicker(false) },
                        confirmButton = {
                            TextButton(onClick = {
                                datePickerState.selectedDateMillis?.let { millis ->
                                    val formattedDate = Instant.ofEpochMilli(millis)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                        .format(formatter)
                                    viewModel.updateDateOfBirth(formattedDate)
                                }
                                viewModel.updateShowDatePicker(false)
                            }) {
                                Text("Select")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState, showModeToggle = false)
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))

                val expanded = remember { mutableStateOf(false) }
                val genderOptions = listOf("Male", "Female", "Other")

                ExposedDropdownMenuBox(
                    expanded = expanded.value,
                    onExpandedChange = { expanded.value = !expanded.value }
                ) {
                    OutlinedTextField(
                        value = gender,
                        onValueChange = {},
                        label = { Text("Gender", fontSize = 13.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(16.dp),
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { expanded.value = !expanded.value }) {
                                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown Icon")
                            }
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false }
                    ) {
                        genderOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    viewModel.updateGender(option)
                                    expanded.value = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (firstname.isBlank() || lastname.isBlank() || username.isBlank()) {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        } else {
                            isLoading = true
                            val userId = currentUser?.uid
                            if (userId != null) {
                                SaveUserDataToFirestore(
                                    user = User(
                                        id = userId,
                                        name = firstname,
                                        toko = toko,
                                        email = currentUser.email.toString(),
                                        country = "Indonesia",
                                        dateOfBirth = dateOfBirth,
                                        gender = gender
                                    ),
                                    context = context
                                ) { isSuccess ->
                                    isLoading = false
                                    if (isSuccess) {
                                        Toast.makeText(context, "Register successful!", Toast.LENGTH_SHORT).show()
                                        navController.navigate("home")
                                    } else {
                                        Toast.makeText(context, "Failed to register. Try again!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = button),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isLoading
                ) {
                    Text(
                        text = if (isLoading) "Loading..." else "Register",
                        color = textbutton,
                        fontSize = 18.sp
                    )
                }

                TextButton(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        navController.navigate("login")
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Back to Login", color = textbutton2)
                }
            }
        }
    }
}
