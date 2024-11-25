//package com.example.myapplication.Views.NavBar
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.res.colorResource
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavDestination
//import androidx.navigation.NavDestination.Companion.hierarchy
//import androidx.navigation.NavGraph.Companion.findStartDestination
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import com.example.myapplication.R
//
//
//@Composable
//fun NavBar() {
//    val navController = rememberNavController()
//
//    Scaffold(
//        modifier = Modifier.fillMaxSize(),
//        bottomBar = { BottomBar(navController = navController) },
//        containerColor = Color.White
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .verticalScroll(rememberScrollState()) // Menambahkan scrolling ke konten
//        ) {
//            BottomNavGraph(navController = navController)
//        }
//    }
//}
//
//
//@Composable
//fun BottomBar(navController: NavHostController) {
//    val screens = listOf(
//        BottomBarScreen.Home,
//        BottomBarScreen.DataBase,
//        BottomBarScreen.History,
//        BottomBarScreen.UserProfile
//    )
//
//    val navStackBackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navStackBackEntry?.destination
//
//    Row(
//        modifier = Modifier
//            .height(60.dp)
//            .background(colorResource(id = R.color.navbar))
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceEvenly,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        screens.forEach { screen ->
//            AddItem(
//                screen = screen,
//                currentDestination = currentDestination,
//                navController = navController
//            )
//        }
//    }
//
//}
//
//@Composable
//fun AddItem(
//    screen: BottomBarScreen,
//    currentDestination: NavDestination?,
//    navController: NavHostController
//) {
//    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
//
//    val background =
//        if (selected) colorResource(id = R.color.navbar_icon_background)
//        else Color.Transparent
//
//    val contentColor =
//        if (selected) Color.White else Color.Black
//
//    Box(
//        modifier = Modifier
//            .height(40.dp)
//            .clip(CircleShape)
//            .background(background)
//            .clickable(onClick = {
//                navController.navigate(screen.route) {
//                    popUpTo(navController.graph.findStartDestination().id)
//                    launchSingleTop = true
//                }
//            })
//    ) {
//        Row(
//            modifier = Modifier
//                .padding(start = 10.dp, end = 10.dp, top = 8.dp, bottom = 8.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(4.dp)
//        ) {
//            Icon(
//                painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
//                contentDescription = "icon",
//                tint = contentColor
//            )
//            AnimatedVisibility(visible = selected) {
//                Text(
//                    text = screen.title,
//                    color = contentColor
//                )
//            }
//        }
//    }
//}
//
//@Composable
//@Preview
//fun BottomNavPreview() {
//    NavBar()
//}