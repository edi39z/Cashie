package com.example.myapplication.navbar

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

    // database
    object Databases: BottomBarScreen(
        route = "database",
        title = "Database",
        icon = R.drawable.database,
        icon_focused = R.drawable.database_focused
    )
    object Cashier: BottomBarScreen(
        route = "kasir",
        title = "Cashier",
        icon = R.drawable.casier,
        icon_focused = R.drawable.casier_focused
    )

    // for Hitory
    object History: BottomBarScreen(
        route = "history",
        title = "History",
        icon = R.drawable.historys,
        icon_focused = R.drawable.historys
    )

    object Profiel: BottomBarScreen(
        route = "profiel",
        title = "Profiel",
        icon = R.drawable.person,
        icon_focused = R.drawable.person_focused
    )

}
