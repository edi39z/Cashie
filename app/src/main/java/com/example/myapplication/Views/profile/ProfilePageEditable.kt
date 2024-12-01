//package com.example.myapplication.views.profile
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.myapplication.data.User
//
//@Composable
//fun ProfilePageEditable(
//    user: User,
//    viewModel: ProfileViewModel,
//    onSave: () -> Unit
//) {
//    var name by remember { mutableStateOf(user.name) }
//    var dateOfBirth by remember { mutableStateOf(user.dateOfBirth) }
//    var gender by remember { mutableStateOf(user.gender) }
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        OutlinedTextField(
//            value = name,
//            onValueChange = { name = it },
//            label = { Text("Name") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = dateOfBirth,
//            onValueChange = { dateOfBirth = it },
//            label = { Text("Date of Birth") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//
//        OutlinedTextField(
//            value = gender,
//            onValueChange = { gender = it },
//            label = { Text("Gender") },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {
//                if (name.isNotBlank() && dateOfBirth.isNotBlank()) {
//                    viewModel.updateUserProfile(
//                        user.copy(name = name, dateOfBirth = dateOfBirth, gender = gender)
//                    ) { success ->
//                        if (success) {
//                            onSave()
//                        } else {
//                            snackbarHostState.showSnackbar("Failed to update profile.")
//                        }
//                    }
//                } else {
//                    snackbarHostState.showSnackbar("Name and Date of Birth are required.")
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Save Changes")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(
//            onClick = { onSave() },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
//        ) {
//            Text("Cancel")
//        }
//    }
//
//    SnackbarHost(hostState = snackbarHostState, modifier = Modifier.align(Alignment.BottomCenter))
//}
