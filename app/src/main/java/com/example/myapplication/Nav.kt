import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

class Nav : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FABCenteredBottomNavigation()
        }
    }
}

@Composable
fun FABCenteredBottomNavigation() {
    val items = listOf("Home", "Search", "FAB", "Profile", "Settings")
    var selectedIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape,
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Opsi di kiri FAB
                    items.take(2).forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                val icon = when (item) {
                                    "Home" -> Icons.Default.Home
                                    "Search" -> Icons.Default.Search
                                    else -> Icons.Default.Home
                                }
                                Icon(imageVector = icon, contentDescription = item)
                            },
                            label = { Text(item, fontSize = 10.sp) },
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f)) // Reservasi ruang untuk FAB

                    // Opsi di kanan FAB
                    items.takeLast(2).forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                val icon = when (item) {
                                    "Profile" -> Icons.Default.Person
                                    "Settings" -> Icons.Default.Settings
                                    else -> Icons.Default.Home
                                }
                                Icon(imageVector = icon, contentDescription = item)
                            },
                            label = { Text(item, fontSize = 10.sp) },
                            selected = selectedIndex == index + 3, // Index dimulai dari opsi kanan
                            onClick = { selectedIndex = index + 3 },
                            selectedContentColor = Color.White,
                            unselectedContentColor = Color.Gray
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { selectedIndex = 2 }, // Pilih FAB saat diklik
                backgroundColor = Color(0xFFFDDE55)

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cart), // Muat gambar dari drawable
                    contentDescription = "Cart",
                    tint = Color.White
                )
            }
        },
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center // Posisi FAB diatur ke tengah
    ) { padding ->
        // Konten layar
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Selected: ${items[selectedIndex]}",
                style = MaterialTheme.typography.h4
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFABCenteredBottomNavigation() {
    FABCenteredBottomNavigation()
}
