package com.example.myapplication.Views.NavBar

import com.example.myapplication.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {

    // for home
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.home,
        icon_focused = R.drawable.home_focused
    )

    // for report
    object DataBase: BottomBarScreen(
        route = "data",
        title = "DataBase",
        icon = R.drawable.file,
        icon_focused = R.drawable.file_focus
    )
    object History : BottomBarScreen(
        route = "history",
        title = "History",
        icon = R.drawable.cart,
        icon_focused = R.drawable.cart_focused
    )

    // for Finis
    object UserProfile: BottomBarScreen(
        route = "profile",
        title = "Profile",
        icon = R.drawable.person,
        icon_focused = R.drawable.person_focused
    )

}