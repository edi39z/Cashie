package com.example.myapplication.`fun`

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navbar.BottomNavGraph
import com.example.myapplication.views.auth.`fun`.AuthManager
import com.example.myapplication.views.auth.register.RegisterScreen
import com.example.myapplication.views.auth.signin.LoginPage
import com.example.myapplication.views.auth.signup.SignupPage


@Composable
fun Navigation( modifier : Modifier = Modifier, authManager: AuthManager,appContext: Context){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "register"
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