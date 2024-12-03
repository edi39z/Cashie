package com.example.myapplication.navbar


import android.content.Context
import android.util.Log
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
import com.example.myapplication.views.casier.kasir
import com.example.myapplication.views.database.AddProductPage
import com.example.myapplication.views.database.DataPage
import com.example.myapplication.views.database.EditProductPage
import com.example.myapplication.views.history.HistoryPage
import com.example.myapplication.views.home.HomePage
import com.example.myapplication.views.profile.ProfilePageWithViewModel
import com.example.myapplication.views.profile.ProfileViewModel
import com.google.firebase.auth.FirebaseAuth


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
                composable("AddProductScreen") {
                    AddProductPage { product ->
                        val userId = FirebaseAuth.getInstance().currentUser?.uid
                        if (userId != null) {
                            productViewModel.addProduct(userId, product,context = appContext)
                            navController.popBackStack() // Kembali ke halaman sebelumnya
                        } else {
                                Log.e("AddProduct", "User ID tidak ditemukan")
                        }
                    }
                }

                composable("EditProductScreen/{productId}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("productId") ?: ""
                    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""

                    if (userId.isNotEmpty()) {
                        EditProductPage(navController = navController, productViewModel = productViewModel, userId = userId, productId = productId)
                    } else {
                        // Tangani jika userId kosong
                    }
                }




                composable(route = BottomBarScreen.Cashier.route) {
                kasir()
                }
                composable(route = BottomBarScreen.History.route) {
                    HistoryPage()
                }
                composable(route = BottomBarScreen.Profiel.route) {
                    ProfilePageWithViewModel(profileViewModel, navController)
                }
            }
        }
    }
}




