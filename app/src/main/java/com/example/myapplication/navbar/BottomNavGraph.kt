package com.example.myapplication.navbar


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.views.casier.CashierPage
import com.example.myapplication.views.history.HistoryPage
import com.example.myapplication.views.home.HomePage
import com.example.myapplication.views.database.DataPage
import com.example.myapplication.views.profile.ProfilePage


@Composable
fun BottomNavGraph(navHostController: NavController) {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomBar(navController)
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(
                navController = navController,
                startDestination = BottomBarScreen.Home.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = BottomBarScreen.Home.route) {
                    HomePage(navHostController)
                }
                composable(route = BottomBarScreen.Databases.route) {
                    DataPage()
                }
                composable(route = BottomBarScreen.Cashier.route) {
                    CashierPage(navHostController)
                }
                composable(route = BottomBarScreen.History.route) {
                    HistoryPage()
                }
                composable(route = BottomBarScreen.Profiel.route) {
                   ProfilePage()
                }
            }
        }
    }
}

