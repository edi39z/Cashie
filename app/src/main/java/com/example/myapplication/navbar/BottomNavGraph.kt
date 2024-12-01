package com.example.myapplication.navbar


import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.views.casier.ScannerScreen
import com.example.myapplication.views.database.DataPage
import com.example.myapplication.views.history.HistoryPage
import com.example.myapplication.views.home.HomePage
import com.example.myapplication.views.profile.ProfilePageWithViewModel


@Composable
fun BottomNavGraph(navHostController: NavController, appContext: Context) {
    val navController = rememberNavController()
    val barcodeScanner = remember { com.example.myapplication.views.casier.`fun`.BarcodeScanner(appContext = appContext) }

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
                    DataPage(navController)
                }
                composable(route = BottomBarScreen.Cashier.route) {
                    ScannerScreen(
                        barcodeScanner = barcodeScanner,
                        navController = navController
                    )
                }
                composable(route = BottomBarScreen.History.route) {
                    HistoryPage()
                }
                composable(route = BottomBarScreen.Profiel.route) {
                    ProfilePageWithViewModel()
                }
            }
        }
    }
}



