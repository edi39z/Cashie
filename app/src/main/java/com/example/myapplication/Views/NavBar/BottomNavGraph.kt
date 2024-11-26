/*
package com.example.myapplication.Views.NavBar

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.Views.DataPage
import com.example.myapplication.Views.HistoryPage
import com.example.myapplication.Views.HomePage
import com.example.myapplication.Views.UserProfilePage


@Composable
fun BottomNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route)
        {
            HomePage()
        }
        composable(route = BottomBarScreen.DataBase.route)
        {
           DataPage()

        }
        composable(route = BottomBarScreen.History.route)
        {
            HistoryPage()
        }
        composable(route = BottomBarScreen.UserProfile.route)
        {
           UserProfilePage()
        }
    }
}


*/
