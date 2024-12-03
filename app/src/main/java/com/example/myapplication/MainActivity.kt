    package com.example.myapplication

    import android.os.Bundle
    import androidx.activity.ComponentActivity
    import androidx.activity.compose.setContent
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material3.Scaffold
    import androidx.compose.ui.Modifier
    import com.example.myapplication.`fun`.Navigation
    import com.example.myapplication.ui.theme.MyApplicationTheme
    import com.example.myapplication.views.auth.`fun`.AuthManager


    class MainActivity : ComponentActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
    //        enableEdgeToEdge()
            val authManager = AuthManager(this)
            setContent {
                MyApplicationTheme {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        Navigation(
                            modifier = Modifier.padding(innerPadding),
                            authManager = authManager,
                            appContext = this@MainActivity
                        )
                    }
                }
            }
        }
    }
