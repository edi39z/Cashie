package com.example.myapplication.navbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.textbutton

@Composable
fun NavBar(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = { BottomBar(navController = navController as NavHostController) },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            BottomNavGraph(navController)
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Databases,
        BottomBarScreen.Cashier,
        BottomBarScreen.History,
        BottomBarScreen.Profiel
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp) // Tinggi navbar
    ) {
        // Navbar dengan potongan (cutout)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp) // Tinggi navbar tanpa floating
                .background(colorResource(id = R.color.navbar)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            screens.forEach { screen ->
                if (screen.route != BottomBarScreen.Cashier.route) {
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navController = navController
                    )
                } else {
                    Spacer(modifier = Modifier.width(74.dp)) // Ruang untuk floating icon
                }
            }
        }

        // Floating Icon untuk Cashier
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter) // Posisi di tengah
                .offset(y = (-36).dp) // Mengapung di atas navbar
                .size(72.dp) // Ukuran lingkaran floating
                .clip(CircleShape)
                .background(colorResource(id = R.color.navbar_icon_background))
                .clickable(onClick = {
                    navController.navigate(BottomBarScreen.Cashier.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                }),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = if (currentDestination?.route == BottomBarScreen.Cashier.route) BottomBarScreen.Cashier.icon_focused else BottomBarScreen.Cashier.icon),
                contentDescription = BottomBarScreen.Cashier.title,
                tint = Color.White,
                modifier = Modifier.size(52.dp) // Ukuran ikon floating
            )
        }
    }
}

@Composable
fun AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    Column(
        modifier = Modifier
            .width(74.dp) // Setiap item memiliki lebar 74dp
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
            contentDescription = screen.title,
            modifier = Modifier.size(24.dp), // Ikon berukuran 24dp
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.height(4.dp)) // Jarak kecil antara ikon dan teks
        // Menambahkan animasi visibilitas pada teks
        AnimatedVisibility(visible = selected) {
            Text(
                text = screen.title,
                fontSize = 12.sp, // Font ukuran 12sp
                color = (textbutton)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    val navController = rememberNavController()
    BottomBar(navController)
}
