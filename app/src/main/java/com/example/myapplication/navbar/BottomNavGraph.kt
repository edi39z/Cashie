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
import com.example.myapplication.product.ProductViewModel
import com.example.myapplication.views.casier.ScannerScreen
import com.example.myapplication.views.database.DataPage
import com.example.myapplication.views.history.HistoryPage
import com.example.myapplication.views.home.HomePage
import com.example.myapplication.views.profile.ProfilePageWithViewModel
import com.example.myapplication.views.profile.ProfileViewModel


@Composable
fun BottomNavGraph(navHostController: NavController, appContext: Context) {
    val navController = rememberNavController()
    val barcodeScanner = remember { com.example.myapplication.views.casier.`fun`.BarcodeScanner(appContext = appContext) }
    val productViewModel = remember { ProductViewModel() }
    val profileViewModel = remember { ProfileViewModel() } // Tambahkan ViewModel di sini

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
                    HomePage(navMainController = navHostController, profileViewModel = profileViewModel)
                }
                composable(route = BottomBarScreen.Databases.route) {
                    DataPage(navController = navController, productViewModel = productViewModel)
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
                    ProfilePageWithViewModel(profileViewModel)
                }
            }
        }
    }
}




