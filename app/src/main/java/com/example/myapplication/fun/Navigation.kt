package com.example.myapplication.`fun`

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navbar.BottomNavGraph
import com.example.myapplication.views.auth.`fun`.AuthManager
import com.example.myapplication.views.auth.register.RegisterScreen
import com.example.myapplication.views.auth.signin.LoginPage
import com.example.myapplication.views.auth.signup.SignupPage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@Composable
fun Navigation( modifier : Modifier = Modifier, authManager: AuthManager,appContext: Context){
    val navController = rememberNavController()
    var startDestination by remember{ mutableStateOf("login") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
            if (Firebase.auth.currentUser != null) {
                val userExists = Firebase.firestore.collection("users")
                    .document(Firebase.auth.currentUser!!.uid).get().await()
                    .exists()
                if (userExists) {
                    startDestination = "home"
                } else {
                    startDestination = "register"
                }

            } else {
                startDestination = "login"
            }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login"){
            LoginPage(modifier, navController)
        }
        composable("signup"){
            SignupPage(modifier, navController)
        }
        composable("register"){
            RegisterScreen(modifier, navController)
        }

        composable("home"){
            BottomNavGraph(
                navHostController = navController,
                appContext = appContext
            )
        }
    }
}